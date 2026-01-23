package top.duofeng.test.demo.service.impl;

import cn.hutool.core.text.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.common.FakeModelEnum;
import top.duofeng.test.demo.common.OuterSystemEnum;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.OuterService;

@Service
@Slf4j
public class OuterCServiceImpl implements OuterService {
    private final WebClient webClient;

    public OuterCServiceImpl(WebClient.Builder clientBuilder) {
        this.webClient = clientBuilder
                .baseUrl("http://localhost:8801")
                .build();
    }

    @Override
    public WebClient getWebClient() {
        return webClient;
    }

    @Override
    public OuterSystemEnum getOuterEnum() {
        return OuterSystemEnum.SYS_THIRD;
    }
}
