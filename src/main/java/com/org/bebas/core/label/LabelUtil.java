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
 * 标签下拉工具类
 *
 * @author wuHao
 * @date 2022/11/11 10:57
 */
@Slf4j
public class LabelUtil {

    private List<?> list;

    public static LabelUtil setValue(List<?> list) {
        LabelUtil _build = new LabelUtil();
        _build.list = list;
        return _build;
    }

    /**
     * 构建下拉
     *
     * @param labelFunc label
     * @param valueFunc value
     * @param <P1>
     * @param <P2>
     * @return
     */
    public <P1, P2> List<LabelOption<String, String>> buildSelect(Func1<P1, ?> labelFunc, Func1<P2, ?> valueFunc) {
        Assert.notNull(labelFunc);
        Assert.notNull(valueFunc);
        if (CollUtil.isEmpty(this.list)) {
            log.error("构建下拉失败，列表为空");
            return CollUtil.newArrayList();
        }
        if (Objects.isNull(labelFunc) || Objects.isNull(valueFunc)) {
            log.error("构建下拉失败，参数为空");
            return CollUtil.newArrayList();
        }
        String labelFieldName = LambdaUtil.getFieldName(labelFunc);
        String valueFieldName = LambdaUtil.getFieldName(valueFunc);
        return this.list.stream().map(item -> {
            LabelOption<String, String> optionLabel = new LabelOption<>();
            Object label = ReflectUtil.getFieldValue(item, labelFieldName);
            Object value = ReflectUtil.getFieldValue(item, valueFieldName);
            optionLabel.setLabel(String.valueOf(label));
            optionLabel.setValue(String.valueOf(value));
            return optionLabel;
        }).collect(Collectors.toList());
    }

}
