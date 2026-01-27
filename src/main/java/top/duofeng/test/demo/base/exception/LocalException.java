package top.duofeng.test.demo.base.exception;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.exception.LocalException
 * author Rorschach
 * dateTime 2026/1/11 23:47
 */
public class LocalException extends RuntimeException{

    public LocalException(String message) {
        super(message);
    }

    public LocalException(String message, Throwable cause) {
        super(message, cause);
    }
}
