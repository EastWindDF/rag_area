package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.FeedbackResultItem
 * author Rorschach
 * dateTime 2026/1/28 0:49
 */
@Data
@Schema(title = "反馈结果项")
public class FeedbackResultItem implements Serializable {
    @Schema(title = "会话ID")
    private String session_id;
    @Schema(title = "问题")
    private String question;
    @Schema(title = "开始时间")
    private LocalDateTime gmt_begin;
    @Schema(title = "结束时间")
    private LocalDateTime gmt_end;
    @Schema(title = "回答")
    private String answer;
    @Schema(title = "附件信息")
    private List<ChatCitation> citations;
    @Schema(title = "反馈评论")
    private FeedbackComment feedback;
}
