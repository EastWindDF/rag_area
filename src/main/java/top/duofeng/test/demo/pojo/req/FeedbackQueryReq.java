package top.duofeng.test.demo.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.req.FeedbackQueryReq
 * author Rorschach
 * dateTime 2026/1/28 0:58
 */
@Data
@Schema(title = "反馈查询请求")
public class FeedbackQueryReq implements Serializable {
    @Schema(title = "模型厂商")
    private String provider;
    @Schema(title = "查询开始时间")
    private LocalDateTime start_time;
    @Schema(title = "查询结束时间")
    private LocalDateTime end_time;
}
