package com.org.bebas.core.label;

import com.org.bebas.utils.bean.ReflectUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @description:
 * @since 2023/4/11 8:34
 * @Version 1.0
 */
@Slf4j
public class LabelBuilder<T> {

    private List<T> list;

    private List<LabelOption<String, String>> valueList;

    public static <T> LabelBuilder<T> setValue(List<T> list) {
        if (Objects.isNull(list)) {
            throw new IllegalArgumentException("列表不能为空");
        }
        LabelBuilder<T> builder = new LabelBuilder<>();
        builder.list = list;
        return builder;
    }

    public LabelBuilder<T> select(Function<T, Object> labelFunc, Function<T, Object> valueFunc) {
        if (Objects.isNull(labelFunc)) {
            throw new IllegalArgumentException("labelFunc不能为空");
        }
        if (Objects.isNull(valueFunc)) {
            throw new IllegalArgumentException("valueFunc不能为空");
        }
        if (this.list == null || this.list.isEmpty()) {
            log.error("构建下拉失败，列表为空");
            return this;
        }
        String labelFieldName = ReflectUtils.getFieldName(labelFunc);
        String valueFieldName = ReflectUtils.getFieldName(valueFunc);
        this.valueList = this.list.stream().map(item -> {
            LabelOption<String, String> optionLabel = new LabelOption<>();
            Object label = ReflectUtils.getFieldValue(item, labelFieldName);
            Object value = ReflectUtils.getFieldValue(item, valueFieldName);
            optionLabel.setLabel(String.valueOf(label));
            optionLabel.setValue(String.valueOf(value));
            return optionLabel;
        }).collect(Collectors.toList());
        return this;
    }

    public List<LabelOption<String, String>> build() {
        return this.valueList;
    }

}