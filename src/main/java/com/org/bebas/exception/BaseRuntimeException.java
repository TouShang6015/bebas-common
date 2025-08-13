package com.org.bebas.exception;

/**
 * @author WuHao
 * @since 2022/5/13 17:39
 */
public class BaseRuntimeException extends RuntimeException {

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public <T extends BaseRuntimeException> T error(String message){
        this.message = message;
        return (T) this;
    }

}
