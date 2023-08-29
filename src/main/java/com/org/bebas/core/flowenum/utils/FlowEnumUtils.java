package com.org.bebas.core.flowenum.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.org.bebas.core.flowenum.base.FlowBaseEnum;
import com.org.bebas.core.label.LabelOption;
import com.org.bebas.exception.BusinessException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 流程状态枚举工具类
 *
 * @author wyj
 * @date 2022/11/1 15:42
 */
public class FlowEnumUtils {

    /**
     * 根据key获取value
     *
     * @param e
     * @param key
     * @param <E>
     * @return
     */
    public static <E extends FlowBaseEnum> String getValueByKey(E[] e, String key) {
        return Arrays.stream(e).filter(item -> item.getKey().equals(key)).map(E::getValue).findFirst().orElse(null);
    }

    /**
     * 获取枚举下拉
     *
     * @param cls 枚举class类
     * @param <T>
     * @return
     */
    public static <T extends FlowBaseEnum> List<LabelOption<String, String>> getOptionList(Class<T> cls) {
        Assert.notNull(cls);
        T[] constants = cls.getEnumConstants();
        return Arrays.stream(constants).map(t -> {
            LabelOption<String, String> optionLabel = new LabelOption<>();
            optionLabel.setLabel(t.getValue());
            optionLabel.setValue(t.getKey());
            return optionLabel;
        }).collect(Collectors.toList());
    }

    /**
     * 获取枚举下拉 通过类名
     *
     * @param className
     * @param <T>
     * @return
     */
    public static <T extends FlowBaseEnum> List<LabelOption<String, String>> getOptionList(String className) {
        Assert.notNull(className);
        try {
            Class<T> aClass = (Class<T>) Class.forName(className);
            return getOptionList(aClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new BusinessException("无法获取类型");
        }
    }

    /**
     * 通过key获取枚举
     *
     * @param key
     * @param <T>
     * @return
     */
    public static <T extends FlowBaseEnum> T getEnumByKey(String key, Class<T> cls) {
        Assert.notNull(key);
        T[] constants = cls.getEnumConstants();
        return Arrays.stream(constants).filter(item -> item.getKey().equals(key)).findFirst().orElse(null);
    }

    /**
     * 通过keys获取map集合
     *
     * @param keys ,拼接
     * @param cls  ,枚举实体
     * @return
     */
    public static <T extends FlowBaseEnum> Map<String, Boolean> getMapByKeys(String keys, Class<T> cls) {
        if (StrUtil.isEmpty(keys)) {
            return new HashMap<>();
        }
        List<T> selectList = CollUtil.newArrayList();
        for (String key : keys.split(",")) {
            selectList.add(FlowEnumUtils.getEnumByKey(key, cls));
        }

        Map<String, Boolean> map = new HashMap<>();
        for (T enumValue : cls.getEnumConstants()) {
            map.put(((Enum<?>) enumValue).name(), selectList.contains(enumValue));
        }
        return map;
    }

    /**
     * 根据map返回枚举已选择的keys
     *
     * @param map
     * @param cls
     * @param <T>
     * @return
     */
    public static <T extends FlowBaseEnum> String getKeysByMap(Map<String, Boolean> map, Class<T> cls) {
        Assert.notNull(map);
        Assert.notNull(cls);
        T[] constants = cls.getEnumConstants();
        Set<Map.Entry<String, Boolean>> entrySet = map.entrySet();
        StringJoiner stringJoiner = new StringJoiner(",");
        for (T constant : constants) {
            String name = ((Enum<?>) constant).name();
            if (entrySet.stream().filter(e -> e.getKey().equals(name)).map(Map.Entry::getValue).findFirst().orElse(Boolean.FALSE)) {
                stringJoiner.add(constant.getKey());
            }
        }
        return stringJoiner.toString();
    }

}
