package com.org.bebas.utils.tree.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuHao
 * @since 2023/3/23 16:53
 */
@Data
public class TreeEntity<M> {

    private Long id;

    private Long parentId;

    private String label;

    private List<M> children;

    // ------ 继承BaseModel字段

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createOper;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updateTime;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updateOper;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int delFlag;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String createTime_;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updateTime_;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Object> paramExtMap = new HashMap<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, String> queryCondition = new HashMap<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Map<String, Boolean> sortCondition = new HashMap<>();
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer version;
    private long page = 1;

    private long size = 10;

}
