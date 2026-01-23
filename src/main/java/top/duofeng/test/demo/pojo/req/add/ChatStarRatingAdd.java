package top.duofeng.test.demo.pojo.req.add;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.req.add.ChatStarRatingAdd
 * author Rorschach
 * dateTime 2026/1/23 1:01
 */
@Data
@Schema(title = "星评请求类")
public class ChatStarRatingAdd implements Serializable {
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "耗时")
    private Integer timeSpent;
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "思考内容")
    private Integer content;
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "回答准确度")
    private Integer accuracy;
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "思考敏感度")
    private Integer sensitivity;
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "引用内容摘要")
    private Integer summary;
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "标签准确度")
    private Integer tagAccuracy;
    @Max(value = 10,message = "最大为5星10分")
    @Schema(title = "智能化处理")
    private Integer processing;
    @Schema(title = "备注")
    private String remark;
}
