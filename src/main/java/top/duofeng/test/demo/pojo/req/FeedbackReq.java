package top.duofeng.test.demo.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.req.FeedbackReq
 * author Rorschach
 * dateTime 2026/1/28 0:56
 */
@Data
@Schema(title = "反馈请求体")
public class FeedbackReq implements Serializable {
    @Schema(title = "私有ID")
    private String priId;
    @Schema(title = "评论")
    private List<String> comments;
}
