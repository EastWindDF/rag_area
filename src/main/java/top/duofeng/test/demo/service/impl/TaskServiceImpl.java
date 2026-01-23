package top.duofeng.test.demo.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.duofeng.test.demo.dao.ConvRecordInfoDao;
import top.duofeng.test.demo.dao.ConvSessionInfoDao;
import top.duofeng.test.demo.dao.TaskInfoDao;
import top.duofeng.test.demo.pojo.ent.ConvSessionInfoEnt;
import top.duofeng.test.demo.pojo.ent.TaskInfoEnt;
import top.duofeng.test.demo.pojo.req.add.TaskAdd;
import top.duofeng.test.demo.pojo.res.CommonTreeDict;
import top.duofeng.test.demo.service.TaskService;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.TaskServiceImpl
 * author Rorschach
 * dateTime 2026/1/11 22:45
 */
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskInfoDao taskInfoDao;
    private final ConvSessionInfoDao sessionInfoDao;

    public TaskServiceImpl(TaskInfoDao taskInfoDao,
                           ConvSessionInfoDao sessionInfoDao) {
        this.taskInfoDao = taskInfoDao;
        this.sessionInfoDao = sessionInfoDao;
    }

    @Override
    public List<CommonTreeDict> list(String userId) {
        List<TaskInfoEnt> tasks = taskInfoDao.findAll((r, q, c) ->
                c.equal(r.get("creatorId"), userId), Sort.by(Sort.Order.desc("gmtCreate")));
        if (CollectionUtils.isEmpty(tasks)) {
            TaskInfoEnt ent = new TaskInfoEnt();
            ent.setTitle("默认任务");
            ent.setCreatorId(userId);
            ent.setCreatorName("用户A");
            ent.setDefaulted(true);
            TaskInfoEnt save = taskInfoDao.save(ent);
            return Lists.newArrayList(new CommonTreeDict(save.getId(), ent.getTitle(), false, null));
        } else {
            Map<String, List<CommonTreeDict>> collect = sessionInfoDao.findAll((r, q, c) ->
                            c.and(c.isTrue(r.get("answered")),
                                    r.get("taskId").in(tasks.stream().map(TaskInfoEnt::getId).toList())), Sort.by(Sort.Order.desc("gmtCreate")))
                    .stream().collect(Collectors.groupingBy(
                            ConvSessionInfoEnt::getTaskId,
                            Collectors.mapping(
                                    ent -> new CommonTreeDict(ent.getId(), ent.getTitle(), true, null),
                                    Collectors.toList()
                            )
                    ));
            return tasks.stream().map(ent -> new CommonTreeDict(ent.getId(), ent.getTitle(),
                    false, collect.get(ent.getId()))).toList();
        }

    }

    @Override
    public Boolean rename(String id, String title, String userId) {
        taskInfoDao.findById(id).ifPresent(ent -> {
            ent.setTitle(title);
            taskInfoDao.saveAndFlush(ent);
        });
        return true;
    }

    @Override
    public Boolean del(String id, String userId) {
        taskInfoDao.deleteById(id);
        List<ConvSessionInfoEnt> sessions = sessionInfoDao.findAll((r, q, c) -> c.equal(r.get("taskId"), id));
        if (!CollectionUtils.isEmpty(sessions)) {
            TaskInfoEnt req = new TaskInfoEnt();
            req.setCreatorId(userId);
            req.setDefaulted(true);
            String defaultTask = taskInfoDao.findOne(Example.of(req)).map(TaskInfoEnt::getId).orElse(null);
            sessions.forEach(ent -> ent.setTaskId(defaultTask));
            sessionInfoDao.saveAllAndFlush(sessions);
        }
        return true;
    }

    @Override
    public Boolean add(TaskAdd add, String userId) {
        taskInfoDao.save(new TaskInfoEnt(add, userId, "用户A"));
        return true;
    }
}
