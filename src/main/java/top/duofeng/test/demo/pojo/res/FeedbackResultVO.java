package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.FeedbackResultVO
 * author Rorschach
 * dateTime 2026/1/28 0:48
 */
@Data
@Schema(title = "查询反馈接口")
public class FeedbackResultVO implements Serializable {
    @Schema(title = "厂商名称")
    private String provider;
    @Schema(title = "反馈结果")
    private List<FeedbackResultItem> items;
}
