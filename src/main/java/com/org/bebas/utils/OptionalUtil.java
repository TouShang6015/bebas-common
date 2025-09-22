package com.org.bebas.utils;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;

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
     * @param t   可能为空的List
     * @param <T> List类型
     * @return 非空List
     */
    public static <T extends List<?>> T ofNullList(T t) {
        return Optional.ofNullable(t).orElseGet(() -> (T) new ArrayList<>());
    }

    /**
     * list获取第一个元素，如果不存在返回 null
     *
     * @param list List集合
     * @param <T>  元素类型
     * @return 第一个元素或null
     */
    public static <T> T listFindFirst(List<T> list) {
        return ofNullList(list).stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /**
     * list如果为空则设置一个默认值
     *
     * @param t            可能为空的List
     * @param defaultValue 默认值
     * @param <T>          List类型
     * @return 非空List
     */
    public static <T extends List<?>> T ofNullListDefault(T t, Object defaultValue) {
        if (t == null || t.isEmpty()) {
            List<Object> newList = new ArrayList<>();
            newList.add(defaultValue);
            return (T) newList;
        } else {
            return t;
        }
    }

    /**
     * Set 非空 Optional
     *
     * @param t   可能为空的Set
     * @param <T> Set类型
     * @return 非空Set
     */
    public static <T extends Set<?>> T ofNullSet(T t) {
        return Optional.ofNullable(t).orElseGet(() -> (T) new HashSet<>());
    }

    /**
     * Set 获取第一个元素，如果不存在返回 null
     *
     * @param set Set集合
     * @param <T> 元素类型
     * @return 第一个元素或null
     */
    public static <T> T setFindFirst(Set<T> set) {
        return ofNullSet(set).stream()
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    /**
     * Set 如果为空则设置一个默认值
     *
     * @param t            可能为空的Set
     * @param defaultValue 默认值
     * @param <T>          Set类型
     * @return 非空Set
     */
    public static <T extends Set<?>> T ofNullSetDefault(T t, Object defaultValue) {
        if (t == null || t.isEmpty()) {
            Set<Object> newSet = new HashSet<>();
            newSet.add(defaultValue);
            return (T) newSet;
        } else {
            return t;
        }
    }

    /**
     * BigDecimal 非空 Optional
     *
     * @param t   可能为空的BigDecimal
     * @param <T> BigDecimal类型
     * @return 非空BigDecimal
     */
    public static <T extends BigDecimal> T ofNullBigDecimal(T t) {
        return Optional.ofNullable(t).orElse((T) BigDecimal.ZERO);
    }

    /**
     * Long 非空 Optional
     *
     * @param t   可能为空的Long
     * @param <T> Long类型
     * @return 非空Long
     */
    public static <T extends Long> T ofNullLong(T t) {
        return Optional.ofNullable(t).orElse((T) NumberUtils.LONG_ZERO);
    }

    /**
     * Long 非空 Optional 赋予自定义默认值
     *
     * @param t            可能为空的Long
     * @param defaultValue 默认值
     * @param <T>          Long类型
     * @return 非空Long
     */
    public static <T extends Long> T ofNullLong(T t, Long defaultValue) {
        return Optional.ofNullable(t).orElse((T) defaultValue);
    }

    /**
     * 获取Stream，如果集合为空则返回空Stream
     *
     * @param collection 集合
     * @param <T>        元素类型
     * @return Stream
     */
    public static <T> Stream<T> ofNullCollection(Collection<T> collection) {
        return Optional.ofNullable(collection)
                .map(Collection::stream)
                .orElseGet(Stream::empty);
    }

    /**
     * 获取数组的Stream，如果数组为空则返回空Stream
     *
     * @param array 数组
     * @param <T>   元素类型
     * @return Stream
     */
    public static <T> Stream<T> ofNullArray(T[] array) {
        return Optional.ofNullable(array)
                .map(Arrays::stream)
                .orElseGet(Stream::empty);
    }

    /**
     * 字符串非空Optional
     *
     * @param str 可能为空的字符串
     * @return 非空字符串
     */
    public static String ofNullString(String str) {
        return Optional.ofNullable(str).orElse("");
    }

    /**
     * 字符串非空Optional，带有默认值
     *
     * @param str          可能为空的字符串
     * @param defaultValue 默认值
     * @return 非空字符串
     */
    public static String ofNullString(String str, String defaultValue) {
        return Optional.ofNullable(str).orElse(defaultValue);
    }
}