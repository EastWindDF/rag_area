package top.duofeng.test.demo.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.config.RagAreaConfig;
import top.duofeng.test.demo.dao.ChatCitationInfoDao;
import top.duofeng.test.demo.dao.ConvRecordInfoDao;
import top.duofeng.test.demo.dao.ConvSessionInfoDao;
import top.duofeng.test.demo.dao.SessPriMappingDao;
import top.duofeng.test.demo.pojo.dto.ConvPriMappingDTO;
import top.duofeng.test.demo.pojo.ent.ChatCitationInfoEnt;
import top.duofeng.test.demo.pojo.ent.ConvRecordInfoEnt;
import top.duofeng.test.demo.pojo.ent.ConvSessionInfoEnt;
import top.duofeng.test.demo.pojo.req.ChatMessage;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatChoice;
import top.duofeng.test.demo.pojo.res.ChatCitation;
import top.duofeng.test.demo.pojo.res.ChatDelta;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.CombineAIService;
import top.duofeng.test.demo.service.ConversationService;
import top.duofeng.test.demo.service.OuterService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static top.duofeng.test.demo.common.StrConst.BLANK;

@Service
@Slf4j
public class CombineAIServiceImpl implements CombineAIService {
    private final SessPriMappingDao sessPriMappingDao;
    private final ConvRecordInfoDao convInfoDao;
    private final ChatCitationInfoDao chatCitationInfoDao;
    private final ConversationService conversationService;
    private final ConvSessionInfoDao convSessionInfoDao;
    private final OuterService outerService;
    private final Map<String, NormalCodeName> maskMap;

    public CombineAIServiceImpl(OuterService outerService,
                                RagAreaConfig ragAreaConfig,
                                ChatCitationInfoDao chatCitationInfoDao,
                                SessPriMappingDao sessPriMappingDao, ConvRecordInfoDao convInfoDao,
                                ConversationService conversationService, ConvSessionInfoDao convSessionInfoDao) {
        this.sessPriMappingDao = sessPriMappingDao;
        this.convInfoDao = convInfoDao;
        this.chatCitationInfoDao = chatCitationInfoDao;
        this.conversationService = conversationService;
        this.convSessionInfoDao = convSessionInfoDao;
        this.outerService = outerService;
        this.maskMap = ragAreaConfig.getMaskMap();
    }

    @Override
    public Flux<ChatResponseVO> combineChat(ChatMsgReq req, String userId) {
        ConvPriMappingDTO conv = conversationService.createConv(req, userId);
        conversationService.updateQuestion(req);
        Map<String, Pair<NormalCodeName, String>> priMapping = conv.getPriMapping();
        LocalDateTime begin = LocalDateTime.now();
        List<Flux<ChatResponseVO>> fluxStream = outerService.chats(req, priMapping);
        Map<String, List<ChatResponseVO>> resultMap = Maps.newConcurrentMap();
        Map<String, LocalDateTime> timeMap = Maps.newConcurrentMap();
        Flux<ChatResponseVO> merge = Flux.merge(fluxStream);
        merge.subscribe(item -> copeMap(item, resultMap, timeMap),
                error -> log.error("FLUX订阅接口问题", error),
                () -> saveConvRecords(begin, req.getSession_id(), resultMap, timeMap));
        return merge;
    }

    @Override
    public Flux<ChatResponseVO> chatSingle(ChatMsgReq req, String userId, boolean isPri) {
        String priId = req.getPriId();
        final NormalCodeName[] modelEnum = new NormalCodeName[1];
        final LocalDateTime[] times = new LocalDateTime[1];
        final String question = Optional.of(req).map(ChatMsgReq::getMessages)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(list -> list.get(0))
                .map(ChatMessage::getContent)
                .orElse(null);
        Flux<ChatResponseVO> flux = sessPriMappingDao.findOne((r, q, c) -> c.equal(r.get("priId"), priId))
                .map(ent -> {
                    modelEnum[0] = maskMap.get(ent.getMaskCode());
                    Optional<ConvSessionInfoEnt> sessionInfo = convSessionInfoDao.findById(ent.getSessId());
                    if (isPri) {
                        sessionInfo
                                .map(ConvSessionInfoEnt::getQuestion)
                                .ifPresent(str -> req.getMessages().add(0, new ChatMessage(null, str)));
                    }
                    sessionInfo.filter(info -> !StringUtils.hasText(info.getQuestion()))
                            .ifPresent(info -> {
                                info.setQuestion(question);
                                assert question != null;
                                info.setTitle(question.substring(0, Integer.min(200, question.length())));
                                convSessionInfoDao.saveAndFlush(info);
                            });
                    return ent.getSysCode();
                }).map(code -> {
                    assert modelEnum[0] != null;
                    return outerService.chatSingle(code, Pair.of(modelEnum[0], priId), req)
                            .doOnNext(resp -> times[0] = LocalDateTime.now());
                })
                .orElse(Flux.empty());
        List<ChatResponseVO> results = Lists.newArrayList();
        AtomicReference<LocalDateTime> time = new AtomicReference<>(LocalDateTime.now());
        flux.subscribe(item -> addResults(results, item, time),
                error -> log.error("FLUX订阅接口问题", error),
                () -> saveConvRecord(times[0], results, time.get(), req, isPri));
        return flux;
    }

    private void addResults(List<ChatResponseVO> results, ChatResponseVO vo, AtomicReference<LocalDateTime> times) {
        results.add(vo);
        times.set(LocalDateTime.now());
    }

    private void copeMap(ChatResponseVO vo, Map<String, List<ChatResponseVO>> map, Map<String, LocalDateTime> timeMap) {
        Optional.ofNullable(vo)
                .ifPresent(e -> {
                    map.merge(e.getPrivateId(), Lists.newArrayList(e), (v1, v2) -> {
                        v1.addAll(v2);
                        return v1;
                    });
                    timeMap.put(e.getPrivateId(), LocalDateTime.now());
                });
    }

    private String getResponseContext(ChatResponseVO vo) {
        return Optional.ofNullable(vo.getChoices())
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(list -> list.get(0))
                .map(ChatChoice::getDelta)
                .map(ChatDelta::getContent)
                .orElse(BLANK);
    }

    private void saveConvRecord(LocalDateTime gmtBegin,
                                List<ChatResponseVO> results,
                                LocalDateTime endTime,
                                ChatMsgReq req, boolean isPri) {
        String answer = BLANK;
        List<ChatCitation> ents = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(results)) {
            answer = results.stream().map(ChatResponseVO::getChoices)
                    .filter(list -> !CollectionUtils.isEmpty(list))
                    .map(list -> list.get(0))
                    .map(ChatChoice::getDelta)
                    .filter(Objects::nonNull)
                    .map(ChatDelta::getContent)
                    .filter(StringUtils::hasText)
                    .collect(Collectors.joining());
            results.stream().map(ChatResponseVO::getCitations)
                    .filter(list -> !CollectionUtils.isEmpty(list))
                    .forEach(ents::addAll);
        }
        ConvRecordInfoEnt ent = new ConvRecordInfoEnt();
        ent.setPriId(req.getPriId());
        ent.setGmtBegin(gmtBegin);
        ent.setAnswer(answer);
        ent.setGmtEnd(endTime);
        ent.setCommon(!isPri);
        ent = convInfoDao.saveAndFlush(ent);
        String convId = ent.getId();
        if (!CollectionUtils.isEmpty(ents)) {
            List<ChatCitationInfoEnt> collect = ents.stream().filter(obj -> Objects.nonNull(obj) && StringUtils.hasText(obj.getId()))
                    .map(ChatCitationInfoEnt::new)
                    .collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(collect)) {
                chatCitationInfoDao.saveAllAndFlush(collect);
            }
        }
        if (!isPri) {
            convSessionInfoDao.findOne((r, q, c) ->
                            c.and(c.equal(r.get("id"), req.getSession_id()),
                                    c.isFalse(r.get("answered"))))
                    .ifPresent(info -> {
                        info.setAnswered(true);
                        info.setGmtModified(LocalDateTime.now());
                        convSessionInfoDao.saveAndFlush(info);
                    });
        }
    }

    private void saveConvRecords(LocalDateTime begin, String sessId,
                                 Map<String, List<ChatResponseVO>> resultMap,
                                 Map<String, LocalDateTime> timeMap) {
        Map<String, List<ChatCitationInfoEnt>> citationMap = Maps.newConcurrentMap();
        Map<String, String> recordIdMap = convInfoDao.saveAllAndFlush(resultMap.entrySet().stream().map(
                entry -> {
                    List<ChatResponseVO> list = entry.getValue();
                    String answer = list.stream().map(e -> e.getChoices().get(0).getDelta().getContent())
                            .collect(Collectors.joining());
                    ConvRecordInfoEnt ent = new ConvRecordInfoEnt();
                    ent.setPriId(entry.getKey());
                    ent.setGmtBegin(begin);
                    ent.setGmtEnd(timeMap.get(entry.getKey()));
                    ent.setCommon(Boolean.TRUE);
                    ent.setAnswer(answer);
                    List<ChatCitationInfoEnt> citations = Lists.newArrayList();
                    citationMap.put(entry.getKey(), citations);
                    list.stream().map(ChatResponseVO::getCitations)
                            .filter(tmp -> !CollectionUtils.isEmpty(tmp))
                            .map(tmp -> tmp.stream().map(ChatCitationInfoEnt::new).toList())
                            .forEach(citations::addAll);
                    return ent;
                }).toList()).stream().collect(Collectors.toMap(
                ConvRecordInfoEnt::getPriId,
                ConvRecordInfoEnt::getId,
                (v1, v2) -> v1
        ));
        List<ChatCitationInfoEnt> citationInfos = Lists.newArrayList();
        citationMap.forEach((k, v) -> {
            for (int i = 0; i < v.size(); i++) {
                ChatCitationInfoEnt infoEnt = v.get(i);
                infoEnt.setOrderNum(i);
                infoEnt.setConvId(recordIdMap.get(k));
                citationInfos.add(infoEnt);
            }
        });
        chatCitationInfoDao.saveAllAndFlush(citationInfos);
        convSessionInfoDao.findById(sessId).ifPresent(info -> {
            info.setAnswered(Boolean.TRUE);
            convSessionInfoDao.save(info);
        });
    }


}
