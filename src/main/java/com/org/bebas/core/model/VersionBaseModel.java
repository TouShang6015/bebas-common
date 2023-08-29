package com.org.bebas.core.model;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

/**
 * @author wyj
 * @date 2023/3/14 17:02
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class VersionBaseModel extends BaseModel {

    /**
     * 版本号
     */
    @Version
    @ApiModelProperty(value = "乐观锁", dataType = "int")
    private Integer version;

    /**
     * 初始化前操作
     */
    @Override
    public void afterInitModel() {
        super.afterInitModel();
        if (Objects.isNull(this.version)) {
            this.version = NumberUtils.INTEGER_ZERO;
        }
    }
}
