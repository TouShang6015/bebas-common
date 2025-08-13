package com.org.bebas.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回实体
 * @author WuHao
 * @since 2022/5/18 10:48
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Page<T extends BaseModel> {

    /**
     * 当前页码
     */
    private long page;
    /**
     * 每页总行数
     */
    private long size;
    /**
     * 列表数据
     */
    private List<T> row;
    /**
     * 总行数
     */
    private long total;

}
