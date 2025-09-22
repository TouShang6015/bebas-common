package com.org.bebas.core.label;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.CollectionUtils;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 标签下拉工具类
 * <p>
 * 该工具类用于将任意对象列表转换为标签选项列表，支持通过lambda表达式指定标签和值的字段
 * </p>
 *
 * @author WuHao
 * @since 2022/11/11 10:57
 */
@Slf4j
public class LabelUtil {

    /**
     * 待处理的对象列表
     */
    private List<?> list;

    /**
     * 设置待处理的对象列表
     *
     * @param list 待处理的对象列表
     * @return LabelUtil实例
     */
    public static LabelUtil setValue(List<?> list) {
        LabelUtil _build = new LabelUtil();
        _build.list = list;
        return _build;
    }

    /**
     * 通过lambda表达式获取字段名称
     * <p>
     * 该方法使用Java的序列化lambda机制来提取字段名称。通过调用lambda表达式的writeReplace方法，
     * 获取SerializedLambda对象，然后从实现方法名推导出字段名。
     * </p>
     *
     * @param func lambda表达式，例如User::getName
     * @param <T>  lambda表达式的目标类型
     * @return 字段名称，如"name"
     * @throws RuntimeException 当无法从lambda表达式提取字段名时抛出
     */
    private <T> String getFieldName(Function<T, ?> func) {
        try {
            // 通过反射获取SerializedLambda
            Method writeReplace = func.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(true);
            SerializedLambda serializedLambda = (SerializedLambda) writeReplace.invoke(func);

            // 从方法名推导字段名
            String methodName = serializedLambda.getImplMethodName();
            if (methodName.startsWith("get")) {
                String fieldName = methodName.substring(3);
                return Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
            } else if (methodName.startsWith("is")) {
                String fieldName = methodName.substring(2);
                return Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1);
            }
            return methodName;
        } catch (Exception e) {
            throw new RuntimeException("无法从lambda表达式中提取字段名: " + func, e);
        }
    }

    /**
     * 获取对象指定字段的值
     * <p>
     * 使用Apache Commons的FieldUtils来获取对象字段的值，支持访问私有字段。
     * </p>
     *
     * @param obj       目标对象
     * @param fieldName 字段名称
     * @return 字段的值，如果字段不存在或访问失败则返回null
     */
    private Object getFieldValue(Object obj, String fieldName) {
        try {
            return FieldUtils.readField(obj, fieldName, true);
        } catch (IllegalAccessException e) {
            log.error("获取字段值失败，对象类型: {}，字段名: {}", obj.getClass().getName(), fieldName, e);
            return null;
        }
    }

    /**
     * 构建下拉选项列表
     * <p>
     * 通过指定的标签字段和值字段，将对象列表转换为标签选项列表。
     * 标签和值都将以字符串形式返回。
     * </p>
     *
     * @param labelFunc 获取标签字段的lambda表达式，例如: User::getName
     * @param valueFunc 获取值字段的lambda表达式，例如: User::getId
     * @param <P1>      标签字段所属对象类型
     * @param <P2>      值字段所属对象类型
     * @return 标签选项列表，每个选项包含标签和值
     */
    public <P1, P2> List<LabelOption<String, String>> buildSelect(Function<P1, ?> labelFunc, Function<P2, ?> valueFunc) {
        if (Objects.isNull(labelFunc) || Objects.isNull(valueFunc)) {
            log.error("构建下拉失败，参数为空");
            return new ArrayList<>();
        }
        if (CollectionUtils.isEmpty(this.list)) {
            log.error("构建下拉失败，列表为空");
            return new ArrayList<>();
        }
        String labelFieldName = getFieldName(labelFunc);
        String valueFieldName = getFieldName(valueFunc);
        return this.list.stream().map(item -> {
            LabelOption<String, String> optionLabel = new LabelOption<>();
            Object label = getFieldValue(item, labelFieldName);
            Object value = getFieldValue(item, valueFieldName);
            optionLabel.setLabel(String.valueOf(label));
            optionLabel.setValue(String.valueOf(value));
            return optionLabel;
        }).collect(Collectors.toList());
    }

}
