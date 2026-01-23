package top.duofeng.test.demo.dao;

import org.springframework.stereotype.Repository;
import top.duofeng.test.demo.base.dao.CommonDao;
import top.duofeng.test.demo.pojo.ent.TaskInfoEnt;

/**
 * project test-demo
 * path top.duofeng.test.demo.dao.TaskInfoDao
 * author Rorschach
 * dateTime 2026/1/11 17:42
 */
@Repository
public interface TaskInfoDao extends CommonDao<TaskInfoEnt, String> {
}
