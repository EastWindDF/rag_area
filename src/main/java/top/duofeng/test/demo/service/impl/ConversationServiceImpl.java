package top.duofeng.test.demo.service.impl;

import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.base.pojo.OuterServiceInfo;
import top.duofeng.test.demo.base.utils.LocalIdGenerator;
import top.duofeng.test.demo.config.RagAreaConfig;
import top.duofeng.test.demo.dao.ChatStarRatingDao;
import top.duofeng.test.demo.dao.ConvRecordInfoDao;
import top.duofeng.test.demo.dao.ConvSessionInfoDao;
import top.duofeng.test.demo.dao.SessPriMappingDao;
import top.duofeng.test.demo.pojo.dto.ConvPriMappingDTO;
import top.duofeng.test.demo.pojo.ent.*;
import top.duofeng.test.demo.pojo.req.ChatMessage;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.req.add.ChatStarRatingAdd;
import top.duofeng.test.demo.pojo.res.*;
import top.duofeng.test.demo.service.ConversationService;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static top.duofeng.test.demo.common.StrConst.CHAT_SESS_PREFIX;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.ConversationServiceImpl
 * author Rorschach
 * dateTime 2026/1/11 21:30
 */
@Service
@Slf4j
public class ConversationServiceImpl implements ConversationService {
    private final ConvSessionInfoDao convSessionInfoDao;
    private final SessPriMappingDao sessPriMappingDao;
    private final ConvRecordInfoDao recordInfoDao;
    private final ChatStarRatingDao chatStarRatingDao;
    private final List<NormalCodeName> maskProps;
    private final Map<String, NormalCodeName> maskMap;
    private final List<OuterServiceInfo> services;


    public ConversationServiceImpl(
            RagAreaConfig ragAreaConfig,
            ConvSessionInfoDao convSessionInfoDao,
            ConvRecordInfoDao recordInfoDao,
            ChatStarRatingDao chatStarRatingDao,
            SessPriMappingDao sessPriMappingDao) {
        this.convSessionInfoDao = convSessionInfoDao;
        this.recordInfoDao = recordInfoDao;
        this.sessPriMappingDao = sessPriMappingDao;
        this.chatStarRatingDao = chatStarRatingDao;
        this.maskProps = ragAreaConfig.getMaskProps();
        this.maskMap = ragAreaConfig.getMaskProps().stream().collect(Collectors.toMap(
                NormalCodeName::getCode,
                Function.identity(),
                (v1,v2)->v1));
        this.services = ragAreaConfig.getOuterServices();
    }

    @Override
    public HistoryChatVO his(String id, String priId, String userId) {
        HistoryChatVO vo = new HistoryChatVO();
        vo.setSessionId(id);
        ConvSessionInfoEnt convSess = convSessionInfoDao.findById(id).orElse(null);
        Optional.ofNullable(convSess)
                .map(ConvSessionInfoEnt::getQuestion)
                .ifPresent(vo::setQuestion);
        List<SessPriMappingEnt> all = sessPriMappingDao
                .findAll((r, q, c) -> c.and(c.equal(r.get("sessId"), id),
                        StringUtils.hasText(priId) ? c.equal(r.get("priId"), priId) : c.isNotNull(r.get("priId"))
                ));
        List<String> list = all.stream().map(SessPriMappingEnt::getPriId).toList();
        Map<String, List<ConvRecordInfoEnt>> collect = recordInfoDao
                .findAll((r, q, c) ->
                        c.and(
                                r.get("priId").in(list),
                                StringUtils.hasText(priId) ? c.equal(r.get("priId"), priId) : c.isTrue(r.get("common"))
                        ), Sort.by(Sort.Direction.ASC, "gmtBegin")).stream().collect(
                        Collectors.groupingBy(ConvRecordInfoEnt::getPriId)
                );
        vo.setChatMap(all.stream().collect(Collectors.toMap(e ->
                        maskMap.get(e.getMaskCode()).getCode(),
                e -> toResponse(e, collect.get(e.getPriId())),
                (v1, v2) -> v1
        )));
        return vo;
    }

    private List<ChatRespWithLikedVO> toResponse(SessPriMappingEnt mappingEnt, List<ConvRecordInfoEnt> records) {
        return records.stream().filter(ConvRecordInfoEnt::getCommon)
                .map(ent -> {
                    ChatRespWithLikedVO vo = new ChatRespWithLikedVO();
                    vo.setObject(null);
                    vo.setMaskCode(maskMap.get(mappingEnt.getMaskCode()).getCode());
                    vo.setCreated(ent.getGmtEnd().toEpochSecond(ZoneOffset.ofHours(8)));
                    vo.setPrivateId(ent.getPriId());
                    vo.setMaskName(mappingEnt.getMaskName());
                    vo.setSession_id(mappingEnt.getSessId());
                    ChatChoice choice = new ChatChoice();
                    ChatDelta delta = new ChatDelta();
                    delta.setContent(ent.getAnswer());
                    choice.setIndex(0);
                    choice.setDelta(delta);
                    vo.setChoices(Lists.newArrayList(choice));
                    Optional.ofNullable(ent.getChatCitations())
                            .filter(list -> !CollectionUtils.isEmpty(list))
                            .map(list -> list.stream()
                                    .map(ChatCitationInfoEnt::toVO).toList())
                            .ifPresent(vo::setCitations);
                    vo.setLiked(Optional.ofNullable(mappingEnt.getLiked()).map(num->num>0).orElse(Boolean.FALSE));
                    return vo;
                }).toList();

    }

    @Override
    public Boolean like(String priId, String userId) {
        SessPriMappingEnt req = new SessPriMappingEnt();
        req.setPriId(priId);
        sessPriMappingDao.findOne(Example.of(req, ExampleMatcher.matching()))
                .ifPresent(ent -> {
                    ent.setLiked(ent.getLiked() + 1);
                    sessPriMappingDao.saveAndFlush(ent);
                });
        return Boolean.TRUE;
    }

    @Override
    public ConvPriMappingDTO createConv(ChatMsgReq req, String userId) {
        String sessionId = req.getSession_id();
        String question = getQuestionFromReq(req);
        if (!StringUtils.hasText(sessionId)) {
            sessionId = CHAT_SESS_PREFIX.concat(LocalIdGenerator.getStrId());
            saveConversation(Optional.ofNullable(question).orElse("新对话"), sessionId, req.getTaskId());
        }
        return new ConvPriMappingDTO(sessionId, getSortMap(sessionId));
    }

    @Override
    public void updateQuestion(ChatMsgReq req) {
        String sessionId = req.getSession_id();
        convSessionInfoDao.findById(sessionId).ifPresent(obj -> {
            obj.setQuestion(getQuestionFromReq(req));
            obj.setGmtModified(LocalDateTime.now());
            convSessionInfoDao.saveAndFlush(obj);
        });
    }

    @Override
    public Boolean rating(ChatStarRatingAdd add, String userId) {
        chatStarRatingDao.saveAndFlush(new ChatStarRatingEnt(add, userId));
        return Boolean.TRUE;
    }

    @Override
    public Boolean unlike(String priId, String userId) {
        SessPriMappingEnt req = new SessPriMappingEnt();
        req.setPriId(priId);
        sessPriMappingDao.findOne(Example.of(req, ExampleMatcher.matching()))
                .ifPresent(ent -> {
                    ent.setLiked(ent.getLiked() - 1);
                    sessPriMappingDao.saveAndFlush(ent);
                });
        chatStarRatingDao.delete((r, q, c) -> c.equal(r.get("priId"), priId));
        return Boolean.TRUE;
    }

    private String getQuestionFromReq(ChatMsgReq req) {
        return Optional.ofNullable(req).map(ChatMsgReq::getMessages)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .map(list -> list.get(0))
                .map(ChatMessage::getContent).orElse(null);
    }

    private Map<String, Pair<NormalCodeName, String>> genSortMap(String sessionId) {
        Stack<Integer> stack = new Stack<>();
        int size = maskProps.size();
        while (stack.size() < size) {
            int i = Math.abs(RandomUtil.randomInt() % size);
            if (!stack.contains(i)) {
                stack.add(i);
            }
        }
        Map<String, Pair<NormalCodeName, String>> result = Maps.newHashMap();
        services.forEach(k -> {
            Integer pop = stack.pop();
            NormalCodeName normalCodeName = maskProps.get(pop);
            result.put(k.getCode(), Pair.of(normalCodeName, genPrivateId(pop, sessionId, k.getCode())));
        });
        sessPriMappingDao.saveAll(result.entrySet().stream().map(entry ->
                new SessPriMappingEnt(sessionId, entry.getKey(), entry.getValue())).toList());
        return result;
    }

    private Map<String, Pair<NormalCodeName, String>> getSortMap(String sessionId) {
        List<SessPriMappingEnt> all = sessPriMappingDao.findAll((root, query, cb) ->
                cb.equal(root.get("sessId"), sessionId));
        if (!CollectionUtils.isEmpty(all)) {
            return all.stream().collect(Collectors.toMap(
                    SessPriMappingEnt::getSysCode,
                    e -> Pair.of(Objects.requireNonNull(maskMap.get(e.getMaskCode())),
                            e.getPriId()),
                    (v1, v2) -> v1
            ));
        }
        return genSortMap(sessionId);
    }


    private void saveConversation(String question, String sessionId, String taskId) {
        ConvSessionInfoEnt ent = new ConvSessionInfoEnt();
        ent.setTitle(question.substring(0, Integer.min(200, question.length())));
        ent.setId(sessionId);
        ent.setTaskId(taskId);
        convSessionInfoDao.save(ent);
    }

    private String genPrivateId(Integer sort, String sessionId, String sysCode) {
        String join = String.join(StrPool.DASHED, sessionId, sort + "", sysCode);
        return DigestUtils.md5DigestAsHex(join.getBytes());
    }
}
