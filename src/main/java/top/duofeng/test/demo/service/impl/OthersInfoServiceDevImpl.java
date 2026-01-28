package top.duofeng.test.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import top.duofeng.test.demo.config.RagAreaConfig;
import top.duofeng.test.demo.dao.ConvRecordInfoDao;
import top.duofeng.test.demo.dao.ConvSessionInfoDao;
import top.duofeng.test.demo.dao.FeedbackCommentDao;
import top.duofeng.test.demo.dao.SessPriMappingDao;
import top.duofeng.test.demo.pojo.ent.FeedbackCommentEnt;
import top.duofeng.test.demo.pojo.req.FeedbackQueryReq;
import top.duofeng.test.demo.pojo.req.FeedbackReq;
import top.duofeng.test.demo.pojo.res.FeedbackComment;
import top.duofeng.test.demo.pojo.res.FeedbackResultVO;
import top.duofeng.test.demo.pojo.res.ReferenceDetailVO;
import top.duofeng.test.demo.service.OthersInfoService;
import top.duofeng.test.demo.utils.DataCreateUtil;

import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.local.OthersInfoServiceLocalImpl
 * author Rorschach
 * dateTime 2026/1/28 1:09
 */
@Service
@Slf4j
public class OthersInfoServiceDevImpl implements OthersInfoService {
    private final RagAreaConfig config;
    private final FeedbackCommentDao commentDao;
    private final ConvRecordInfoDao recordInfoDao;
    private final ConvSessionInfoDao sessionInfoDao;
    private final SessPriMappingDao mappingDao;
    private final FeedbackCommentDao feedbackCommentDao;
    private final RestTemplate restTemplate;

    public OthersInfoServiceDevImpl(RagAreaConfig config,
                                    FeedbackCommentDao feedbackCommentDao,
                                    FeedbackCommentDao commentDao,
                                    ConvRecordInfoDao recordInfoDao,
                                    ConvSessionInfoDao sessionInfoDao, SessPriMappingDao mappingDao,
                                    RestTemplate restTemplate) {
        this.config = config;
        this.commentDao = commentDao;
        this.recordInfoDao = recordInfoDao;
        this.sessionInfoDao = sessionInfoDao;
        this.mappingDao = mappingDao;
        this.restTemplate = restTemplate;
        this.feedbackCommentDao = feedbackCommentDao;
    }

    @Override
    public ReferenceDetailVO refDetail(String refId, String priId) {
        if(config.getMockEnabled()){
            return DataCreateUtil.fakReferenceDetail();
        }
        return null;
    }

    @Override
    public Boolean feedback(FeedbackReq param) {
        if(StringUtils.hasText(param.getPriId()) && !CollectionUtils.isEmpty(param.getComments())){
            List<FeedbackCommentEnt> list = param.getComments().stream().map(comment -> {
                FeedbackCommentEnt commentEnt = new FeedbackCommentEnt();
                commentEnt.setComment(comment);
                commentEnt.setPriId(param.getPriId());
                return commentEnt;
            }).toList();
            commentDao.saveAll(list);
        }
        return Boolean.TRUE;
    }

    @Override
    public FeedbackResultVO feedbackQuery(FeedbackQueryReq param) {
        return null;
    }
}
