package com.org.bebas.enums.result;

import com.org.bebas.constants.HttpStatus;
import com.org.bebas.utils.MessageUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author WuHao
 * @since 2022/5/12 17:37
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultEnum {

    SUCCESS(HttpStatus.SUCCESS, MessageUtils.message("common.request.query.success"), true),
    SUCCESS_INSERT(HttpStatus.SUCCESS, MessageUtils.message("common.request.insert.success"), true),
    SUCCESS_UPDATE(HttpStatus.SUCCESS, MessageUtils.message("common.request.update.success"), true),
    SUCCESS_DELETE(HttpStatus.SUCCESS, MessageUtils.message("common.request.delete.success"), true),
    FAIL(HttpStatus.ERROR, MessageUtils.message("common.request.query.fail"), false),
    FAIL_INSERT(HttpStatus.ERROR, MessageUtils.message("common.request.insert.fail"), false),
    FAIL_UPDATE(HttpStatus.ERROR, MessageUtils.message("common.request.update.fail"), false),
    FAIL_DELETE(HttpStatus.ERROR, MessageUtils.message("common.request.delete.fail"), false),

    ERROR_RUNTIME(HttpStatus.ERROR, MessageUtils.message("common.request.query.fail"), false),
    ;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
        this.status = false;        // true成功 false失败
    }

    ResultEnum(int code, String message, boolean status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    private int code;
    private String message;
    private Object data;
    private boolean status;

}
