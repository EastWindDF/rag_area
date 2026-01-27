package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.FeedbackComment
 * author Rorschach
 * dateTime 2026/1/28 0:52
 */
@Data
@Schema(title = "反馈评价")
public class FeedbackComment implements Serializable {
    @Schema(title = "点赞")
    private Boolean liked;
    @Schema(title = "评价")
    private List<String> comments;
}
