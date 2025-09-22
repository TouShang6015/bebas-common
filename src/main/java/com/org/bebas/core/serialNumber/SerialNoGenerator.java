package com.org.bebas.core.serialNumber;

import com.org.bebas.core.redis.RedisUtil;
import com.org.bebas.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 增强版流水号生成器 (支持动态规则、多组件扩展)
 *
 * @author WuHao
 * @since 2023-01-07 15:57:05
 */
@RequiredArgsConstructor
@Component
public class SerialNoGenerator {

    private final RedisUtil redisUtil;

    // 规则缓存（业务键 -> 生成规则）
    private final Map<String, SerialNoRule> ruleCache = new ConcurrentHashMap<>();

    /**
     * 生成流水号（使用默认规则）
     *
     * @param businessKey 业务标识键
     * @return 完整流水号
     */
    public String generate(String businessKey) {
        return generate(businessKey, new SerialNoRule());
    }

    /**
     * 生成流水号（自定义规则）
     *
     * @param businessKey 业务标识键
     * @param rule        生成规则
     * @return 完整流水号
     */
    public String generate(String businessKey, SerialNoRule rule) {
        if (StringUtils.isEmpty(businessKey)) {
            throw new IllegalArgumentException("业务键不能为空");
        }

        // 获取或创建规则（带缓存）
        SerialNoRule effectiveRule = ruleCache.computeIfAbsent(
                businessKey,
                k -> rule.initializeDefaults()
        );

        // 生成序列号组件
        String prefix = effectiveRule.getPrefixGenerator().apply(businessKey);
        String datePart = effectiveRule.getDateGenerator().apply(businessKey);
        String sequence = generateSequence(businessKey, effectiveRule);

        // 组合完整流水号
        return StringUtils.format(
                effectiveRule.getTemplate(),
                prefix, datePart, sequence
        );
    }

    /**
     * 生成序列号
     */
    private String generateSequence(String businessKey, SerialNoRule rule) {
        // 构建Redis键（包含重置周期标识）
        String redisKey = buildRedisKey(businessKey, rule);

        // 获取序列值（Redis或本地）
        long sequenceValue = useRedis(rule)
                ? redisUtil.incrBy(redisKey, 1)
                : LocalSequenceHolder.getSequence(redisKey).incrementAndGet();

        // 处理序列号重置
        handleReset(redisKey, sequenceValue, rule);

        // 格式化序列号
        return formatSequence(sequenceValue, rule);
    }

    /**
     * 构建Redis存储键
     */
    private String buildRedisKey(String businessKey, SerialNoRule rule) {
        String datePart = rule.getResetMode().getDateSuffix();
        return StringUtils.format("serial:{}:{}:{}",
                rule.getSystemTag(), businessKey, datePart);
    }

    /**
     * 处理序列号重置
     */
    private void handleReset(String redisKey, long currentValue, SerialNoRule rule) {
        if (currentValue == 1 && useRedis(rule)) {
            redisUtil.expire(redisKey, rule.getResetMode().getExpireSeconds());
        }
    }

    /**
     * 格式化序列号
     */
    private String formatSequence(long sequence, SerialNoRule rule) {
        String rawSequence = String.valueOf(sequence);
        int padSize = rule.getDigit() - rawSequence.length();

        if (padSize <= 0) {
            return rawSequence;
        }

        String padding = repeat(rule.getPadChar(), padSize);
        return rule.getPadMode() == PadMode.LEFT
                ? padding + rawSequence
                : rawSequence + padding;
    }

    /**
     * 重复字符指定次数
     *
     * @param c     要重复的字符
     * @param count 重复次数
     * @return 重复后的字符串
     */
    private String repeat(char c, int count) {
        if (count <= 0) {
            return "";
        }
        char[] chars = new char[count];
        for (int i = 0; i < count; i++) {
            chars[i] = c;
        }
        return new String(chars);
    }

    private boolean useRedis(SerialNoRule rule) {
        return rule.isRedisEnabled() && redisUtil != null;
    }
}