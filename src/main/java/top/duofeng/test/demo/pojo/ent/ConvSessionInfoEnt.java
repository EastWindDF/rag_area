package top.duofeng.test.demo.pojo.ent;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.ConvSessionInfoEnt
 * author Rorschach
 * dateTime 2026-01-09 20:05:01
 */

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_conv_session_info")
@Schema(title = "对话会话信息实体类")
public class ConvSessionInfoEnt implements Serializable {

    @Id
    private String id;
    @Column(name = "title")
    private String title;
    @Column(name = "task_id")
    private String taskId;
    @Column(name = "question")
    private String question;
    @Column(name = "answered")
    private Boolean answered;
    @Column(name = "deleted")
    private Boolean deleted;
    @Schema(title = "创建时间")
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;
    @Schema(title = "最后修改时间")
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;
}
