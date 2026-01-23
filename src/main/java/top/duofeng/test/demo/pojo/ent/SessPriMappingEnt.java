package top.duofeng.test.demo.pojo.ent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.util.Pair;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;
import top.duofeng.test.demo.common.FakeModelEnum;
import top.duofeng.test.demo.common.OuterSystemEnum;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.SessPriMappingEnt
 * author Rorschach
 * dateTime 2026-01-09 20:05:01
 */

@Entity
@Data
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_sess_pri_mapping")
@Schema(title = "会话与私有编号映射")
public class SessPriMappingEnt implements Serializable {

    @Id
    @CustomIdGenerator
    private String id;
    @Schema(title = "会话id")
    @Column(name = "sess_id")
    private String sessId;
    @Schema(title = "私有id")
    @Column(name = "pri_id")
    private String priId;
    @Schema(title = "真实系统编号")
    @Column(name = "sys_code")
    private String sysCode;
    @Schema(title = "掩饰名称")
    @Column(name = "mask_name")
    private String maskName;
    @Schema(title = "点赞")
    @Column(name = "liked")
    private Integer liked;
    @Schema(title = "星级")
    @Column(name = "grade")
    private Float grade;
    @Schema(title = "创建时间")
    @Column(name = "gmt_create")
    private LocalDateTime gmtCreate;
    @Schema(title = "最后修改时间")
    @Column(name = "gmt_modified")
    private LocalDateTime gmtModified;
    public SessPriMappingEnt(String sessionId,
                             OuterSystemEnum systemEnum,
                             Pair<FakeModelEnum, String> pair) {
        this.sessId = sessionId;
        this.sysCode = systemEnum.getCode();
        this.maskName = pair.getFirst().getName();
        this.priId = pair.getSecond();
    }
}
