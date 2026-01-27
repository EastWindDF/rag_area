package top.duofeng.test.demo.service;

import top.duofeng.test.demo.pojo.req.add.TaskAdd;
import top.duofeng.test.demo.pojo.res.CommonTreeDict;

import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.TaskService
 * author Rorschach
 * dateTime 2026/1/11 17:06
 */
public interface TaskService {
    List<CommonTreeDict> list(String userId);
    Boolean rename(String id, String title, String userId);
    Boolean del(String id, String userId);
    Boolean add(TaskAdd add, String userId);
}
