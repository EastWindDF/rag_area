package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.Data;
import org.springframework.data.util.Pair;

import java.io.Serializable;
import java.util.Map;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.ConvCreatedVO
 * author Rorschach
 * dateTime 2026/1/13 9:30
 */
@Data
@Schema(title = "会话创建返回类")
public class ConvCreatedVO implements Serializable {
    @Schema(title = "会话ID")
    private String sessionId;
    @Schema(title = "私有会话ID映射 私有ID,模型code")
    private Map<String, String> priIdMapping;
}
