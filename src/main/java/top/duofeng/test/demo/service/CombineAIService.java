package top.duofeng.test.demo.service;

import reactor.core.publisher.Flux;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;

public interface CombineAIService {
    Flux<ChatResponseVO> combineChat(ChatMsgReq req, String userId);
    Flux<ChatResponseVO> chatSingle(ChatMsgReq req, String userId, boolean isPri);
}
