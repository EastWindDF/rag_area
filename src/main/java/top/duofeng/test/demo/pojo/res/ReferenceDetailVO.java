package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.ReferenceDetailVO
 * author Rorschach
 * dateTime 2026/1/28 0:30
 */
@Data
@Schema(title = "引用详情信息")
public class ReferenceDetailVO implements Serializable {
    @Schema(title = "引用的标识ID")
    private String ref_id;
    @Schema(title = "转写内容json数组的字符串化")
    private String content;
    @Schema(title = "翻译内容数组的字符串化。")
    private String trans;
    @Schema(title = "时间点，单位：秒。")
    private Long time_point;
    @Schema(title = "关键要素")
    private KeyElements key_elements;

}
