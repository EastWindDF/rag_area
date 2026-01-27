package top.duofeng.test.demo.pojo.ent;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.ConvInfoEnt
 * author Rorschach
 * dateTime 2026-01-09 20:05:01
 */
@Data
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "tb_conv_record_info")
public class ConvRecordInfoEnt implements Serializable {

    @Id
    /*@GeneratedValue(generator = "snowflakeStr", strategy = )
    @GenericGenerator(name = "snowflakeStr", strategy = "top.fuofeng.test.demo.base.utils.LocalSnowFlakeGenerator")*/
    @CustomIdGenerator
    private String id;
    @Schema(title = "唯一会话ID")
    private String priId;
    @Schema(title = "问题")
    @Column(name = "question")
    private String question;
    @Schema(title = "回答")
    @Column(name = "answer")
    private String answer;
    @Schema(title = "是否为公共问题")
    @Column(name = "common")
    private Boolean common;
    @Schema(title = "问答开始时间")
    @Column(name = "gmt_begin")
    private LocalDateTime gmtBegin;
    @Schema(title = "问答结束时间")
    @Column(name = "gmt_end")
    private LocalDateTime gmtEnd;

    @OneToMany(mappedBy = "convId", cascade = CascadeType.DETACH, orphanRemoval = true)
    private List<ChatCitationInfoEnt> chatCitations;
}
