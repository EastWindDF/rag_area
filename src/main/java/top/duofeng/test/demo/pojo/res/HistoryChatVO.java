package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.HistoryChatVO
 * author Rorschach
 * dateTime 2026/1/11 22:22
 */
@Data
@Schema(title = "历史记录信息")
public class HistoryChatVO implements Serializable {
    private String sessionId;
    private String question;
    private Map<String, List<ChatResponseVO>> chatMap;
}
