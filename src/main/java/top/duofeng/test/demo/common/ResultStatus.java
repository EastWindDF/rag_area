package top.duofeng.test.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultStatus {
    SUCCESS(200, "执行成功!"),
    FAILED(400, "执行失败!"),
    UNAUTHORIZED(401, "认证未通过!"),
    SERVER_ERROR(500, "服务内部错误");

    private final int code;
    private final String msg;
}
