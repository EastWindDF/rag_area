package top.duofeng.test.demo.pojo.ent;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;
import top.duofeng.test.demo.pojo.req.add.ChatStarRatingAdd;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.ChatStarRatingEnt
 * author Rorschach
 * dateTime 2026/1/23 0:18
 */

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_chat_star_rating")
@Schema(title = "对话星评")
public class ChatStarRatingEnt implements Serializable {

    @Id
    @CustomIdGenerator
    private String id;
    @Column(name = "pri_id")
    @Schema(title = "私有ID")
    private String priId;
    @Column(name = "time_spent")
    @Schema(title = "耗时")
    private Integer timeSpent;
    @Column(name = "content")
    @Schema(title = "思考内容")
    private Integer content;
    @Column(name = "accuracy")
    @Schema(title = "回答准确度")
    private Integer accuracy;
    @Column(name = "sensitivity")
    @Schema(title = "思考敏感度")
    private Integer sensitivity;
    @Column(name = "summary")
    @Schema(title = "引用内容摘要")
    private Integer summary;
    @Column(name = "tag_accuracy")
    @Schema(title = "标签准确度")
    private Integer tagAccuracy;
    @Column(name = "processing")
    @Schema(title = "智能化处理")
    private Integer processing;
    @Column(name = "remark")
    @Schema(title = "备注")
    private String remark;
    @Column(name = "user_id")
    @Schema(title = "用户ID")
    private String userId;
    @Column(name = "gmt_create")
    @Schema(title = "插入时间")
    private LocalDateTime gmtCreate;


    public ChatStarRatingEnt(ChatStarRatingAdd add, String userId) {
        BeanUtils.copyProperties(add, this);
        this.userId = userId;
        this.gmtCreate = LocalDateTime.now();
    }
}
