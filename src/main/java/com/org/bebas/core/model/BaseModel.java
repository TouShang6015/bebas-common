package com.org.bebas.core.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.org.bebas.core.validator.group.GroupMainId;
import com.org.bebas.core.validator.group.GroupUpdate;
import com.org.bebas.utils.uuid.SFIDWorker;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 公共实体model
 *
 * @author WuHao
 * @since 2022/5/12 10:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseModel implements Serializable {

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value = "主键", dataType = "Long")
    @NotNull(message = "主键不能为空", groups = {GroupUpdate.class, GroupMainId.class})
    private Long id;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", dataType = "String")
    private String createTime;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者", dataType = "String")
    private String createOper;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间", dataType = "String")
    private String updateTime;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人", dataType = "String")
    private String updateOper;
    /**
     * 删除标识 0 未删除 1 已删除
     */
    @ApiModelProperty(value = "删除标识 0 未删除 1 已删除", dataType = "int")
    private int delFlag;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建时间-第二参数", dataType = "String")
    private String createTime_;
    @TableField(exist = false)
    @ApiModelProperty(value = "修改时间-第二参数", dataType = "String")
    private String updateTime_;

    /**
     * 扩展参数
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "扩展参数", dataType = "Map")
    private Map<String, Object> paramExtMap = new HashMap<>();

    /**
     * key 字段名
     * value 查询条件 eq like
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "自定义查询参数", dataType = "Map")
    private Map<String, String> queryCondition = new HashMap<>();
    /**
     * key 字段名
     * value 排序条件
     * true asc  false desc
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "自定义排序参数", dataType = "Map")
    private Map<String, Boolean> sortCondition = new HashMap<>();

    @TableField(exist = false)
    @ApiModelProperty(value = "分页页码", dataType = "int")
    private long page = 1;

    @TableField(exist = false)
    @ApiModelProperty(value = "分页页数", dataType = "int")
    private long size = 10;

    /**
     * 获取唯一id
     */
    public static Long uniqueId() {
        return SFIDWorker.nextId();
    }

    /**
     * 初始化前操作
     */
    public void afterInitModel() {
    }

}
