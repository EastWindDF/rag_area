package top.duofeng.test.demo.service;

import top.duofeng.test.demo.pojo.dto.ConvPriMappingDTO;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.req.add.ChatStarRatingAdd;
import top.duofeng.test.demo.pojo.res.HistoryChatVO;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.ConversationService
 * author Rorschach
 * dateTime 2026/1/11 17:16
 */
public interface ConversationService {

    HistoryChatVO his(String id, String priId, String userId);

    Boolean like(String priId, String userId);

    ConvPriMappingDTO createConv(ChatMsgReq req, String userId);

    void updateQuestion(ChatMsgReq req);

    Boolean rating(ChatStarRatingAdd add, String userId);

    Boolean unlike(String priId, String userId);
}
