package top.duofeng.test.demo.service;

import com.google.common.collect.Lists;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.common.FakeModelEnum;
import top.duofeng.test.demo.common.OuterSystemEnum;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.utils.DataCreateUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public interface OuterService {


    OuterSystemEnum getOuterEnum();

    WebClient getWebClient();
    default  Flux<ChatResponseVO> chatMessage(ChatMsgReq req , Pair<FakeModelEnum, String> pair) {
        if(DataCreateUtil.isMocked()){
            return DataCreateUtil.genTest(req).doOnNext(re->{
                re.setMaskName(pair.getFirst().getName());
                re.setPrivateId(pair.getSecond());
                re.setMaskCode(pair.getFirst().toString());
            });
        }
        return getWebClient().post()
                .uri(uriBuilder -> uriBuilder.path("/ai/gen-test").build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(req)
                .retrieve()
                .bodyToFlux(ChatResponseVO.class)
                .doOnNext(re->{
                    re.setMaskName(pair.getFirst().getName());
                    re.setPrivateId(pair.getSecond());
                    re.setMaskCode(pair.getFirst().toString());
                });
    }
}
