package com.org.bebas.core.label;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 标签下拉实体
 *
 * @author WuHao
 * @since 2022/7/13 18:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelOption<ValueType, LabelType> {

    private ValueType value;

    private LabelType label;

}
