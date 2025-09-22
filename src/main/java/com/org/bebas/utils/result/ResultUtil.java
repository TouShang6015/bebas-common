package com.org.bebas.utils.result;

import com.alibaba.fastjson2.JSON;
import com.org.bebas.constants.HttpStatus;

import java.util.Objects;

/**
 * @author WuHao
 * @since 2022/5/29 17:08
 */
public class ResultUtil {

    /**
     * 验证结果集是否成功
     *
     * @param result
     * @return
     */
    public static boolean verifySuccess(Result result) {
        if (Objects.isNull(result))
            return false;
        return result.getCode() == HttpStatus.SUCCESS;
    }

    /**
     * 获取结果集数据
     *
     * @param result
     * @param <R>
     * @return
     */
    public static <R> R getData(Result result) {
        if (Objects.isNull(result))
            return null;
        if (Objects.isNull(result.getData()))
            return null;
        return (R) result.getData();
    }

    /**
     * 获取结果集数据根据类型
     *
     * @param result
     * @param cls
     * @param <R>
     * @return
     */
    public static <R> R getData(Result result, Class<R> cls) {
        if (Objects.isNull(result))
            return null;
        if (Objects.isNull(result.getData()))
            return null;
        return JSON.parseObject(JSON.toJSONString(result.getData()), cls);
    }

}
