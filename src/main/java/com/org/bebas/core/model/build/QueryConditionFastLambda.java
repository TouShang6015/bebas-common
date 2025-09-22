package com.org.bebas.core.model.build;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.org.bebas.core.model.BaseModel;
import com.org.bebas.enums.ConditionEnum;
import com.org.bebas.utils.bean.ReflectUtils;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author WuHao
 * @description: model 查询参数构建
 * @Version 1.0
 * @since 2023/4/1$ 14:21$
 */
public class QueryConditionFastLambda<M extends BaseModel> {

    private QueryConditionFast<M> conditionFast;

    /**
     * build构建入口
     *
     * @param model
     * @param <M>
     * @return
     */
    public static synchronized <M extends BaseModel> QueryConditionFastLambda<M> build(M model) {
        Assert.notNull(model, "model不能为空");
        QueryConditionFastLambda<M> fastLambda = new QueryConditionFastLambda<>();
        fastLambda.setConditionFast(QueryConditionFast.<M>build(model));
        return fastLambda;
    }

    /**
     * 查询条件新增(in)
     *
     * @param func
     * @param value
     */
    public QueryConditionFastLambda<M> queryConditionIn(Function<M, Object> func, String value) {
        Assert.notNull(func, "func不能为空");
        Assert.notNull(value, "value不能为空");


        String propName = ReflectUtils.getFieldName(func);
        this.conditionFast.queryConditionIn(propName, value);
        return this;
    }

    public QueryConditionFastLambda<M> queryConditionIn(Function<M, Object> func, Collection<?> list) {
        Assert.notNull(func);
        Assert.notNull(list);
        String propName = ReflectUtils.getFieldName(func);
        String join = list.stream().map(String::valueOf).collect(Collectors.joining(StringPool.COMMA));
        this.conditionFast.queryConditionIn(propName, join);
        return this;
    }

    /**
     * 查询条件新增 lambda
     *
     * @param func
     * @param value
     * @return
     */
    public QueryConditionFastLambda<M> queryCondition(Function<M, Object> func, String value) {
        Assert.notNull(func);
        Assert.notNull(value);
        String fieldName = ReflectUtils.getFieldName(func);
        this.conditionFast.queryCondition(fieldName, value);
        return this;
    }

    /**
     * 查询条件新增 lambda
     *
     * @param func
     * @param conditionEnum
     * @return
     */
    public QueryConditionFastLambda<M> queryCondition(Function<M, Object> func, ConditionEnum conditionEnum) {
        Assert.notNull(func);
        Assert.notNull(conditionEnum);
        this.queryCondition(func, conditionEnum.name());
        return this;
    }

    /**
     * 排序条件新增 lambda
     *
     * @param func
     * @param value
     * @return
     */
    public QueryConditionFastLambda<M> sortCondition(Function<M, Object> func, Boolean value) {
        Assert.notNull(func);
        Assert.notNull(value);
        String fieldName = ReflectUtils.getFieldName(func);
        this.conditionFast.sortCondition(fieldName, value);
        return this;
    }

    private QueryConditionFast<M> getConditionFast() {
        return conditionFast;
    }

    private void setConditionFast(QueryConditionFast<M> conditionFast) {
        this.conditionFast = conditionFast;
    }

}
