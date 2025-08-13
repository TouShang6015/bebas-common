package com.org.bebas.core.serialNumber;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.Getter;

import java.util.Date;

/**
 * 重置模式枚举（内置过期时间）
 */
@Getter
public enum ResetMode {

    // 永不重置
    NEVER("", 0),
    // 按天重置（保留90天）
    DAILY(DatePattern.PURE_DATE_FORMAT.getPattern(), 90),
    // 按周重置
    WEEKLY("yyyyww", 30),
    // 按月重置
    MONTHLY("yyyyMM", 365),
    // 按年重置
    YEARLY("yyyy", Integer.MAX_VALUE);

    private final String datePattern;
    private final int expireDays;
    private final long expireSeconds;

    ResetMode(String datePattern, int expireDays) {
        this.datePattern = datePattern;
        this.expireDays = expireDays;
        this.expireSeconds = expireDays * 86400L;
    }

    public String getDateSuffix() {
        return "never".equals(datePattern) ? "permanent" : DateUtil.format(new Date(), datePattern);
    }
}