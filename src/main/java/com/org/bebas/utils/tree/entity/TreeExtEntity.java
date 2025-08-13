package com.org.bebas.utils.tree.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WuHao
 * @since 2023/3/23 16:53
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class TreeExtEntity<T> extends TreeEntity<T> {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer flag;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String prop1;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String prop2;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String prop3;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String prop4;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String prop5;

}
