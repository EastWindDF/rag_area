package top.duofeng.test.demo.base.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.pojo.CodeName
 * author Rorschach
 * dateTime 2026/1/26 10:23
 */
@Data
public abstract class CodeName<E,T> {
    @Schema(title = "编号")
    protected E code;
    @Schema(title = "名称")
    protected T name;
}
