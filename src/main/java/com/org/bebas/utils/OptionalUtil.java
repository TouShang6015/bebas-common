package com.org.bebas.utils;

import cn.hutool.core.collection.CollUtil;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * optional 工具类
 *
 * @author WuHao
 * @since 2022/9/6 15:52
 */
public class OptionalUtil {

    /**
     * List 非空 Optional
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends List<?>> T ofNullList(T t) {
        return Optional.ofNullable(t).orElseGet(() -> (T) new ArrayList());
    }

    /**
     * list获取第一个元素，如果不存在返回 null
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T listFindFirst(List<T> list) {
        return ofNullList(list).stream().filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * list如果为空则设置一个默认值
     *
     * @param t
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T extends List<?>> T ofNullListDefault(T t, Object defaultValue) {
        if (t == null || t.size() < 1) {
            return (T) Arrays.asList(defaultValue);
        } else {
            return t;
        }
    }

    /**
     * Set 非空 Optional
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends Set<?>> T ofNullSet(T t) {
        return Optional.ofNullable(t).orElseGet(() -> (T) new HashSet<>());
    }

    /**
     * Set 获取第一个元素，如果不存在返回 null
     *
     * @param set
     * @param <T>
     * @return
     */
    public static <T> T setFindFirst(Set<T> set) {
        return ofNullSet(set).stream().filter(Objects::nonNull).findFirst().orElse(null);
    }

    /**
     * Set 如果为空则设置一个默认值
     *
     * @param t
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T extends Set<?>> T ofNullSetDefault(T t, Object defaultValue) {
        if (t == null || t.size() < 1) {
            return (T) CollUtil.newHashSet(defaultValue);
        } else {
            return t;
        }
    }


    /**
     * BigDecimal 非空 Optional
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends BigDecimal> T ofNullBigDecimal(T t) {
        return Optional.ofNullable(t).orElse((T) BigDecimal.ZERO);
    }

    /**
     * Long 非空 Optional
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends Long> T ofNullLong(T t) {
        return Optional.ofNullable(t).orElse((T) NumberUtils.LONG_ZERO);
    }

    /**
     * Long 非空 Optional 赋予自定义默认值
     *
     * @param t
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T extends Long> T ofNullLong(T t, Long defaultValue) {
        return Optional.ofNullable(t).orElse((T) defaultValue);
    }

}
