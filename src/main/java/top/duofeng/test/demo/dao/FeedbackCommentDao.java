package top.duofeng.test.demo.dao;

import org.springframework.stereotype.Repository;
import top.duofeng.test.demo.base.dao.CommonDao;
import top.duofeng.test.demo.pojo.ent.FeedbackCommentEnt;

/**
 * project test-demo
 * path top.duofeng.test.demo.dao.FeedbackCommentDao
 * author Rorschach
 * dateTime 2026/1/28 2:11
 */
@Repository
public interface FeedbackCommentDao extends CommonDao<FeedbackCommentEnt,String> {
}
