package top.duofeng.test.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import top.duofeng.test.demo.base.pojo.ResultDTO;
import top.duofeng.test.demo.pojo.req.add.TaskAdd;
import top.duofeng.test.demo.pojo.res.CommonTreeDict;
import top.duofeng.test.demo.service.TaskService;

import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.controller.TaskController
 * author Rorschach
 * dateTime 2026/1/11 16:57
 */

@RestController
@Tag(name = "任务相关接口")
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/list")
    @Operation(summary = "根据用户ID罗列其所有的任务")
    public ResultDTO<List<CommonTreeDict>> list(@RequestHeader(value = "userId",defaultValue = "mamba") String userId){
        return ResultDTO.success(taskService.list(userId));
    }


    @PostMapping("/add")
    @Operation(summary = "添加任务")
    public ResultDTO<Boolean> add(@RequestBody TaskAdd add,
                                  @RequestHeader(value = "userId",defaultValue = "mamba") String userId){
        return ResultDTO.success(taskService.add(add, userId));
    }

    @GetMapping("/rename")
    @Operation(summary = "修改任务名称")
    public ResultDTO<Boolean> rename(
            @RequestParam("code") String code,
            @RequestParam("title") String title, @RequestHeader(value = "userId",defaultValue = "mamba") String userId){
        return ResultDTO.success(taskService.rename(code, title, userId));
    }

    @GetMapping("/del")
    @Operation(summary = "修改任务名称")
    public ResultDTO<Boolean> del(
            @RequestParam("code") String code, @RequestHeader(value = "userId",defaultValue = "mamba") String userId){
        return ResultDTO.success(taskService.del(code, userId));
    }

}
