package com.org.bebas.core.label;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.func.Func1;
import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.ReflectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
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
        Assert.notNull(list);
        LabelBuilder<T> builder = new LabelBuilder<>();
        builder.list = list;
        return builder;
    }

    public LabelBuilder<T> select(Func1<T, ?> labelFunc, Func1<T, ?> valueFunc) {
        Assert.notNull(labelFunc);
        Assert.notNull(valueFunc);
        if (CollUtil.isEmpty(this.list)) {
            log.error("构建下拉失败，列表为空");
            return this;
        }
        if (Objects.isNull(labelFunc) || Objects.isNull(valueFunc)) {
            log.error("构建下拉失败，参数为空");
            return this;
        }
        String labelFieldName = LambdaUtil.getFieldName(labelFunc);
        String valueFieldName = LambdaUtil.getFieldName(valueFunc);
        this.valueList = this.list.stream().map(item -> {
            LabelOption<String, String> optionLabel = new LabelOption<>();
            Object label = ReflectUtil.getFieldValue(item, labelFieldName);
            Object value = ReflectUtil.getFieldValue(item, valueFieldName);
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
