package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(title = "聊天返回")
public class ChatChoice implements Serializable {
    private Integer index;
    private ChatDelta delta;
    private String finish_reason;
}
