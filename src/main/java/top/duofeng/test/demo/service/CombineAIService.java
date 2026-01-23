package top.duofeng.test.demo.service;

import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.common.FakeModelEnum;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;

public interface CombineAIService {

    Flux<ChatResponseVO> combineChat(ChatMsgReq req);

    Flux<ChatResponseVO> combineChat(ChatMsgReq req, String userId);

    Flux<ChatResponseVO> chatSingle(ChatMsgReq req, String userId, boolean isPri);
}
