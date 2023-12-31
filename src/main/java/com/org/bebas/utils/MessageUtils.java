package com.org.bebas.utils;

import com.org.bebas.core.spring.SpringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

/**
 * 获取i18n资源文件
 */
public class MessageUtils {

    private static final MessageSource messageSource;

    static {
        messageSource = SpringUtils.getBean("messageSource");
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args) {
        return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
    }

    public static String message(int code, Object... args) {
        return message(code + "", args);
    }
}
