package top.duofeng.test.demo.pojo.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Schema(title = "对话请求")
public class ChatMsgReq implements Serializable {
    @NotEmpty
    private String taskId;
    private String priId;
    private List<ChatMessage> messages;
    private String session_id;
    private Date start_time;
    private Date end_time;
}
