package top.duofeng.test.demo.pojo.ent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;
import top.duofeng.test.demo.pojo.req.add.TaskAdd;

import java.io.Serializable;
import java.util.Date;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.TaskIInfoEnt
 * author Rorschach
 * dateTime 2026-01-09 20:05:01
 */


@Entity
@Data
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_task_info")
@Schema(title = "任务列表")
public class TaskInfoEnt implements Serializable {

    @Id
    @CustomIdGenerator
    private String id;

    @Column(name = "title")
    private String title;

    @Column(name = "defaulted")
    private boolean defaulted;

    @Column(name = "creator_id")
    private String creatorId;

    @Column(name = "description")
    private String description;
    @Column(name = "support_unit")
    private String supportUnit;
    @Column(name = "bus_dir")
    private String busDir;
    @Column(name = "work_goal")
    private String workGoal;
    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "gmt_create")
    private Date gmtCreate;



    public TaskInfoEnt(TaskAdd add, String creatorId, String creatorName) {
        BeanUtils.copyProperties(add, this);
        this.creatorId = creatorId;
        this.creatorName = creatorName;

    }
}
