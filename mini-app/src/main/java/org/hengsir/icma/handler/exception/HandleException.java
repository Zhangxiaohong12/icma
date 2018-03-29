package org.hengsir.icma.handler.exception;

/**
 * 处理器基础异常。
 *
 */
public class HandleException extends Exception {

    private String code;            //错误代码

    public String getCode() {
        return code;
    }

    public HandleException() {
        super();
    }

    public HandleException(String code, String message) {
        super(message);
        this.code = code;
    }

    public HandleException(String message, Throwable cause) {
        super(message, cause);
    }

    public HandleException(Throwable cause) {
        super(cause);
    }

    protected HandleException(
        String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
