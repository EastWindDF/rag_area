package top.duofeng.test.demo.pojo.res;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Schema(title = "引用来源")
public class ChatCitation implements Serializable {
    @Schema(title = "引用标识ID")
    private String id;
    @Schema(title = "引用的摘要内容")
    private String summary;
    @Schema(title = "通话开始时间")
    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private LocalDateTime start_time;
    @Schema(title = "通话时长,单位:秒")
    private Integer duration;
    @Schema(title = "主叫号码")
    private String callnumber;
    @Schema(title = "被叫号码")
    private String callednumber;
    @Schema(title = "相关标签,用|分割")
    private String labels;
}
