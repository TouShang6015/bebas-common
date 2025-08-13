package com.org.bebas.utils.result;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.org.bebas.constants.HttpStatus;

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
        if (ObjectUtil.isNull(result))
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
        if (ObjectUtil.isNull(result))
            return null;
        if (ObjectUtil.isNull(result.getData()))
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
        if (ObjectUtil.isNull(result))
            return null;
        if (ObjectUtil.isNull(result.getData()))
            return null;
        return JSON.parseObject(JSON.toJSONString(result.getData()), cls);
    }

}
