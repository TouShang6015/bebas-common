package com.org.bebas.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户异常
 * @author WuHao
 * @since 2022/5/22 20:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserException extends BaseRuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    public UserException() {
    }

    public UserException(String message) {
        this.message = message;
        super.setMessage(message);
    }

    public UserException(String message,int code) {
        this.message = message;
        this.code = code;
        super.setMessage(message);
    }


}
