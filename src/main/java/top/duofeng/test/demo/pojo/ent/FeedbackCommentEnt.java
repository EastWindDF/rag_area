package top.duofeng.test.demo.pojo.ent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;

import java.io.Serializable;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.FeedbackCommentEnt
 * author Rorschach
 * dateTime 2026/1/28 2:12
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_feedback_comment")
@Schema(title = "反馈信息")
public class FeedbackCommentEnt implements Serializable {
    @Id
    @CustomIdGenerator
    private String id;
    @Column(name = "pri_id")
    @Schema(title = "私有ID")
    private String priId;
    @Column(name = "comments")
    @Schema(title = "评论")
    private String comment;
}
