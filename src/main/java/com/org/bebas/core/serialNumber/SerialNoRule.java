package com.org.bebas.core.serialNumber;

import lombok.Getter;

import java.util.function.Function;

/**
 * 流水号生成规则配置
 */
@Getter
public class SerialNoRule {

    private static final int DEFAULT_DIGIT = 6;
    private static final char DEFAULT_PAD_CHAR = '0';
    private static final ResetMode DEFAULT_RESET_MODE = ResetMode.DAILY;
    private static final PadMode DEFAULT_PAD_MODE = PadMode.LEFT;

    // 基础配置
    private String systemTag = "default";
    // 位数
    private int digit = DEFAULT_DIGIT;
    // 占位符
    private char padChar = DEFAULT_PAD_CHAR;
    // 填充模式
    private PadMode padMode = DEFAULT_PAD_MODE;
    // 重置模式
    private ResetMode resetMode = DEFAULT_RESET_MODE;
    // redis启用状态
    private boolean redisEnabled = true;

    // 组件配置（函数式接口）
    private Function<String, String> prefixGenerator = key -> "";
    private Function<String, String> dateGenerator = key -> "";
    // 模板占位符: 前缀/日期/序列号
    private String template = "{}{}{}";

    public SerialNoRule initializeDefaults() {
        // 初始化日期生成器（如果未设置）
        if ("".equals(dateGenerator.apply("test"))) {
            this.dateGenerator = key -> resetMode.getDatePattern();
        }
        return this;
    }

    public SerialNoRule digit(int digit) {
        this.digit = digit;
        return this;
    }

    public SerialNoRule padChar(char padChar) {
        this.padChar = padChar;
        return this;
    }

    public SerialNoRule padMode(PadMode padMode) {
        this.padMode = padMode;
        return this;
    }

    public SerialNoRule resetMode(ResetMode resetMode) {
        this.resetMode = resetMode;
        return this;
    }

    public SerialNoRule redisEnabled(boolean enabled) {
        this.redisEnabled = enabled;
        return this;
    }

    public SerialNoRule systemTag(String tag) {
        this.systemTag = tag;
        return this;
    }

    public SerialNoRule prefixGenerator(Function<String, String> generator) {
        this.prefixGenerator = generator;
        return this;
    }

    public SerialNoRule dateGenerator(Function<String, String> generator) {
        this.dateGenerator = generator;
        return this;
    }

    public SerialNoRule template(String template) {
        this.template = template;
        return this;
    }
}