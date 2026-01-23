package top.duofeng.test.demo.dao;

import org.springframework.stereotype.Repository;
import top.duofeng.test.demo.base.dao.CommonDao;
import top.duofeng.test.demo.pojo.ent.ConvSessionInfoEnt;

/**
 * project test-demo
 * path top.duofeng.test.demo.dao.ConvSessionInfoDao
 * author Rorschach
 * dateTime 2026/1/11 17:43
 */
@Repository
public interface ConvSessionInfoDao extends CommonDao<ConvSessionInfoEnt, String> {
}
