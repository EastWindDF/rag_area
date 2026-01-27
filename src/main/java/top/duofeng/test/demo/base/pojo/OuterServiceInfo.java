package top.duofeng.test.demo.base.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.pojo.OuterSysInfo
 * author Rorschach
 * dateTime 2026/1/26 10:15
 */
@Data
@Schema(title = "外部系统接口配置信息")
public class OuterServiceInfo implements Serializable {
    @Schema(title = "系统名称")
    private String name;
    @Schema(title = "系统编号")
    private String code;
    @Schema(title = "基本请求路径")
    private String base = "http://localhost:8801";
    @Schema(title = "请求路径")
    private String path = "/v1/chat/completions";
}
