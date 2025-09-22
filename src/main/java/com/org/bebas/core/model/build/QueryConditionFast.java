package com.org.bebas.core.model.build;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.org.bebas.core.model.BaseModel;
import com.org.bebas.enums.ConditionEnum;
import com.org.bebas.utils.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.Optional;

import static com.org.bebas.constants.Constants.DEFAULT_IN_PARAM;

/**
 * @author WuHao
 * @description: model 查询参数构建
 * @Version 1.0
 * @since 2023/4/1$ 14:21$
 */
public class QueryConditionFast<M extends BaseModel> {

    private M model;

    /**
     * build构建入口
     *
     * @param model
     * @param <M>
     * @return
     */
    public static synchronized <M extends BaseModel> QueryConditionFast<M> build(M model) {
        Assert.notNull(model);
        QueryConditionFast<M> fastBuilder = new QueryConditionFast<>();
        fastBuilder.model = model;
        return fastBuilder;
    }

    /**
     * 查询条件新增(in)
     *
     * @param propName
     * @param value
     */
    public QueryConditionFast<M> queryConditionIn(String propName, String value) {
        Field propField = FieldUtils.getDeclaredField(model.getClass(), propName);
        Assert.notNull(propField);
        if (StringUtils.isEmpty(propName)) {
            return this;
        }
        if (StringUtils.isEmpty(value)) {
            value = DEFAULT_IN_PARAM;
        }
        this.model.getQueryCondition().put(propName, ConditionEnum.IN.name());
        String extValue = (String) Optional.ofNullable(this.model.getParamExtMap().get(propName)).orElse(DEFAULT_IN_PARAM);
        extValue += StringPool.COMMA + value;
        this.model.getParamExtMap().put(propName, extValue);
        try {
            FieldUtils.writeField(this.model, propName, null, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot set field value: " + propName, e);
        }
        return this;
    }

    /**
     * 查询条件新增
     *
     * @param propName
     * @param value
     * @return
     */
    public QueryConditionFast<M> queryCondition(String propName, String value) {
        Assert.notNull(propName);
        Assert.notNull(value);
        this.model.getQueryCondition().put(propName, value);
        return this;
    }

    /**
     * 查询条件新增
     *
     * @param propName
     * @param conditionEnum
     * @return
     */
    public QueryConditionFast<M> queryCondition(String propName, ConditionEnum conditionEnum) {
        Assert.notNull(propName);
        Assert.notNull(conditionEnum);
        this.queryCondition(propName, conditionEnum.name());
        return this;
    }

    /**
     * 排序条件新增
     *
     * @param propName
     * @param value
     * @return
     */
    public QueryConditionFast<M> sortCondition(String propName, Boolean value) {
        Assert.notNull(propName);
        Assert.notNull(value);
        this.model.getSortCondition().put(propName, value);
        return this;
    }

    private M getModel() {
        return model;
    }

    private void setModel(M model) {
        this.model = model;
    }

}
