package com.org.bebas.core.serialNumber;

import cn.hutool.core.lang.Assert;
import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 流水号生成工具
 *
 * @author wuHao
 * @date 2023-01-07 15:57:05
 */
@RequiredArgsConstructor
@Component
public class SerialNoGenerate {

    private static final int DEFAULT_DIGIT = 6;

    private final RedisUtil redisUtil;

    /**
     * 获取流水号自增
     *
     * @param key
     * @param digit
     * @return
     */
    public String getSerialNo(String key, int digit) {
        return getSerialNo(key, digit, true);
    }

    /**
     * 获取流水号自增
     *
     * @param key            唯一标识
     * @param digit          长度
     * @param ifAppendPrefix 是否添加前缀
     * @return
     */
    public String getSerialNo(String key, int digit, boolean ifAppendPrefix) {
        Assert.notNull(key);
        Long increment = redisUtil.incrBy(key, 1);
        return wholeSerialNo(String.valueOf(increment), digit, ifAppendPrefix);
    }

    /**
     * 获取流水号自增 {@link SerialNoGenerate.DEFAULT_DIGIT}
     *
     * @param key
     * @return
     */
    public String getSerialNo(String key) {
        return getSerialNo(key, DEFAULT_DIGIT);
    }

    /**
     * 完整流水号
     *
     * @param value
     * @param digit
     * @param ifAppendPrefix
     * @return
     */
    public String wholeSerialNo(String value, int digit, boolean ifAppendPrefix) {
        Assert.notNull(value);
        if (!ifAppendPrefix) {
            return value;
        }
        int length = digit - value.length();
        Assert.isFalse(length < 0, () -> new CommonException("序列号生成失败，流水号过长"));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }
        return sb.append(value).toString();
    }

    /**
     * 完整流水号，带占位符的
     *
     * @param value
     * @param digit
     * @return
     */
    public String wholeSerialNo(String value, int digit) {
        return wholeSerialNo(value, digit, true);
    }

}
