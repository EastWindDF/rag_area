package top.duofeng.test.demo.pojo.ent;

import cn.hutool.core.date.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.BeanUtils;
import top.duofeng.test.demo.base.anno.CustomIdGenerator;
import top.duofeng.test.demo.pojo.res.ChatCitation;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.ent.ChatCitationInfoEnt
 * author Rorschach
 * dateTime 2026-01-09 20:05:01
 */
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tb_chat_citation_info")
@Schema(title = "对话附件内容")
public class ChatCitationInfoEnt implements Serializable {
    @Id
    @CustomIdGenerator
    private String id;
    @Schema(title = "唯一会话ID")
    @Column(name = "conv_id")
    private String convId;
    @Schema(title = "引用ID")
    @Column(name = "ref_id")
    private String refId;
    @Column(name = "summary")
    @Schema(title = "引用的摘要内容")
    private String summary;
    @Column(name = "gmt_begin")
    @Schema(title = "通话开始时间")
    private LocalDateTime gmtBegin;
    @Schema(title = "通话时长,单位:秒")
    @Column(name = "duration")
    private Integer duration;
    @Column(name = "call_num")
    @Schema(title = "主叫号码")
    private String callNum;
    @Column(name = "called_num")
    @Schema(title = "被叫号码")
    private String calledNum;
    @Column(name = "labels")
    @Schema(title = "相关标签,用|分割")
    private String labels;
    @Column(name = "order_num")
    @Schema(title = "排序")
    private Integer orderNum;

    public ChatCitationInfoEnt() {

    }

    public ChatCitation toVO(){
        ChatCitation cit = new ChatCitation();
        cit.setId(refId);
        cit.setCallnumber(callNum);
        cit.setLabels(labels);
        cit.setDuration(duration);
        cit.setSummary(summary);
        cit.setStart_time(gmtBegin);
        cit.setCallednumber(calledNum);
        return cit;
    }

    public ChatCitationInfoEnt(ChatCitation vo){
        BeanUtils.copyProperties(vo, this);
        this.id = null;
        this.refId = vo.getId();
        this.calledNum = vo.getCallednumber();
        this.callNum = vo.getCallnumber();
        this.gmtBegin = vo.getStart_time();
    }
}
