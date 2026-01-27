package top.duofeng.test.demo.service;

import top.duofeng.test.demo.pojo.req.FeedbackQueryReq;
import top.duofeng.test.demo.pojo.req.FeedbackReq;
import top.duofeng.test.demo.pojo.res.FeedbackResultVO;
import top.duofeng.test.demo.pojo.res.ReferenceDetailVO;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.OthersInfoService
 * author Rorschach
 * dateTime 2026/1/28 1:07
 */
public interface OthersInfoService {
    ReferenceDetailVO refDetail(String refId, String priId);

    Boolean feedback(FeedbackReq param);

    FeedbackResultVO feedbackQuery(FeedbackQueryReq param);
}
