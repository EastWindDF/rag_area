package top.duofeng.test.demo.base.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import top.duofeng.test.demo.common.ResultStatus;

import java.io.Serializable;

@Data
@Schema(title = "通用结果返回类")
public class ResultDTO<T> implements Serializable {
    private Integer code;
    private String msg;
    private T data;

    public ResultDTO(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultDTO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResultDTO<T> success(String msg) {
        return ResultDTO.success(msg, null);
    }
    public static <T> ResultDTO<T> success(String msg, T data){
        return ResultDTO.of(ResultStatus.SUCCESS.getCode(), msg, data);
    }

    public static <T> ResultDTO<T> success(T data){
        return ResultDTO.success(ResultStatus.SUCCESS.getMsg(), data);
    }

    public static <T> ResultDTO<T> of(Integer code, String msg, T data){
        return new ResultDTO<>(code, msg, data);
    }

    public static <T> ResultDTO<T> fail(String msg) {
        return ResultDTO.of(ResultStatus.FAILED.getCode(), msg ,null);
    }

    public static <T> ResultDTO<T> fail() {
        return ResultDTO.of(ResultStatus.FAILED.getCode(), ResultStatus.FAILED.getMsg() ,null);
    }

    public static <T> ResultDTO<T> fail(String msg, T data){
        return ResultDTO.of(ResultStatus.FAILED.getCode(), msg ,data);
    }

    public static <T> ResultDTO<T> fail(Integer code, String msg) {
        return ResultDTO.of(code, msg, null);
    }

}
