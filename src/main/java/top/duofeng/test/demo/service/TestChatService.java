package top.duofeng.test.demo.service;

import reactor.core.publisher.Flux;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.TestChatService
 * author Rorschach
 * dateTime 2026/1/25 0:30
 */
public interface TestChatService {



    Flux<ChatResponseVO> testChat(ChatMsgReq req);

    Flux<ChatResponseVO> testChat2(ChatMsgReq req);
}
