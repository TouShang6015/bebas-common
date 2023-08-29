package com.org.bebas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公共模块异常
 *
 * @author WhHao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    private int code;

    public CommonException() {
    }

    public CommonException(String message) {
        this.message = message;
        super.setMessage(message);
    }

    public CommonException(String message,int code) {
        this.message = message;
        this.code = code;
        super.setMessage(message);
    }
}