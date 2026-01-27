package top.duofeng.test.demo.base.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.pojo.NormalCodeName
 * author Rorschach
 * dateTime 2026/1/26 10:22
 */
@Data
@Schema(title = "一般编号名称")
public class NormalCodeName implements Serializable {

    @Schema(title = "编号")
    private String code;
    @Schema(title = "名称")
    private String name;

}
