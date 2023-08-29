package com.org.bebas.exception;

/**
 * 全局异常
 *
 * @author WuHao
 */
public class GlobalException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    private int code;

    public GlobalException() {
    }

    public GlobalException(String message) {
        this.message = message;
        super.setMessage(message);
    }

    public GlobalException(String message,int code) {
        this.message = message;
        this.code = code;
        super.setMessage(message);
    }

}