package com.org.bebas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常
 *
 * @author WhHao
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends BaseRuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    private int code;

    public BusinessException() {
    }

    public BusinessException(String message) {
        this.message = message;
        super.setMessage(message);
    }

    public BusinessException(String message,int code) {
        this.message = message;
        this.code = code;
        super.setMessage(message);
    }
}