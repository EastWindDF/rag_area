package top.duofeng.test.demo.pojo.req.add;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.req.add.TaskAdd
 * author Rorschach
 * dateTime 2026/1/13 15:40
 */
@Data
@Schema(title = "任务新增类")
public class TaskAdd implements Serializable {
    @Schema(title = "任务名称")
    private String title;
    @Schema(title = "任务描述")
    private String description;
    @Schema(title = "支撑单位")
    private String supportUnit;
    @Schema(title = "业务方向")
    private String busDir;
    @Schema(title = "工作目标")
    private String workGoal;
}
