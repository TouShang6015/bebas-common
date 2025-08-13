package com.org.bebas.utils.bean;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.ValueFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ReflectionUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author WuHao
 * @since 2022/5/17 23:23
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * 将bean中的空字符串转换为null
     *
     * @param m
     * @param <M>
     * @return
     */
    public static <M> M emptyToNull(M m) {
        if (ObjectUtil.isNull(m))
            return null;
        return (M) JSON.parseObject(
                JSON.toJSONString(m, (ValueFilter) (obj, name, value) -> {
                    if (value instanceof String) {
                        if (StrUtil.isEmpty((CharSequence) value)) return null;
                    }
                    return value;
                }),
                m.getClass()
        );
    }

    /**
     * 将bean中的null初始化值
     *
     * @param m
     * @param <M>
     * @return
     */
    public static <M> M nullToEmptyDefault(M m) {
        if (ObjectUtil.isNull(m))
            return null;
        return (M) JSON.parseObject(
                JSON.toJSONString(m
                        , JSONWriter.Feature.WriteNullStringAsEmpty
                        , JSONWriter.Feature.WriteNullNumberAsZero
                        , JSONWriter.Feature.WriteNullBooleanAsFalse),
                m.getClass()
        );
    }

    /**
     * 获取bean中属性的value值
     *
     * @param m            bean对象
     * @param propertyName 属性名字
     * @param <M>
     * @return
     */
    public static <M> Object valueByPropertyName(M m, String propertyName) {
        AtomicReference<Object> temp = new AtomicReference<>(new Object());
        JSON.toJSONString(m, (ValueFilter) (obj, name, val) -> {
            if (StrUtil.isNotEmpty(name) && name.equals(propertyName)) {
                temp.set(val);
            }
            return null;
        });
        return temp.get();
    }

    /**
     * bean转换Map<String,String>
     *
     * @param obj
     * @return
     */
    public static Map<String, String> convertObjectToMap(Object obj) {
        return Arrays.stream(BeanUtils.getPropertyDescriptors(obj.getClass()))
                .filter(pd -> !"class".equals(pd.getName()))
                .collect(HashMap::new,
                        (map, pd) -> {
                            if (ReflectionUtils.invokeMethod(pd.getReadMethod(), obj) != null) {
                                map.put(pd.getName(), String.valueOf(ReflectionUtils.invokeMethod(pd.getReadMethod(), obj)));
                            }
                        },
                        HashMap::putAll);
    }

}
