package top.duofeng.test.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.base.pojo.OuterServiceInfo;
import top.duofeng.test.demo.config.RagAreaConfig;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.OuterService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.OuterServiceProdImpl
 * author Rorschach
 * dateTime 2026/1/26 19:38
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "prod")
public class OuterServiceProdImpl implements OuterService {

    private static final Map<String, Pair<WebClient, OuterServiceInfo>> WEBCLIENT_MAP = new HashMap<>();

    public OuterServiceProdImpl(RagAreaConfig ragAreaConfig,
                                WebClient.Builder clientBuild) {
        Optional.of(ragAreaConfig).map(RagAreaConfig::getOuterServices)
                .filter(list -> !CollectionUtils.isEmpty(list))
                .ifPresent(list ->
                        list.forEach(e -> WEBCLIENT_MAP.put(e.getCode(), Pair.of(
                                clientBuild.baseUrl(e.getBase()).build(), e
                        ))));
    }

    @Override
    public Flux<ChatResponseVO> chatSingle(String systemCode,
                                           Pair<NormalCodeName, String> priPair,
                                            ChatMsgReq req) {
        return Optional.ofNullable(WEBCLIENT_MAP.get(systemCode))
                .map(pair -> pair.getFirst().post()
                        .uri(uriBuilder -> uriBuilder.path(pair.getSecond().getPath()).build())
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .bodyValue(req)
                        .retrieve()
                        .bodyToFlux(ChatResponseVO.class)
                        .doOnNext(chatResp->{
                            chatResp.setMaskName(priPair.getFirst().getName());
                            chatResp.setMaskCode(priPair.getFirst().getCode());
                            chatResp.setPrivateId(priPair.getSecond());
                        }))
                .orElse(Flux.empty());
    }


}
