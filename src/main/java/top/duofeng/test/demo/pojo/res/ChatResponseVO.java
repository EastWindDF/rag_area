package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Schema(title = "对话响应类")
public class ChatResponseVO implements Serializable {
    private String session_id;
    private String object;
    private Long created;
    private List<ChatChoice> choices;
    private List<ChatCitation> citations;
    private String maskName;
    private String maskCode;
    private String privateId;
}
