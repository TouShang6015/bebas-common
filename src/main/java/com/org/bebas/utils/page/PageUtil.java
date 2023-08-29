package com.org.bebas.utils.page;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.org.bebas.core.model.BaseModel;

import java.util.List;
import java.util.function.Function;

/**
 * @author Wuhao
 * @date 2022/7/25 17:34
 */
public class PageUtil {

    /**
     * 获取IPage对象
     * @param model
     * @param <Model>
     * @param <T>
     * @return
     */
    public static <Model extends BaseModel,T> IPage<T> pageBean(Model model){
        Page<T> page = new Page<>();
        page.setCurrent(model.getPage());
        page.setSize(model.getSize());
        return page;
    }

    /**
     * 获取IPage对象
     * @param model
     * @param cls
     * @param <Model>
     * @param <T>
     * @return
     */
    public static <Model extends BaseModel,T> IPage<T> pageBean(Model model,Class<T> cls){
        Page<T> page = new Page<>();
        page.setCurrent(model.getPage());
        page.setSize(model.getSize());
        return page;
    }

    /**
     * IPage 泛型转换
     * @param modelIPage
     * @param convertFunction
     * @param <Dto>
     * @param <Model>
     * @return
     */
    public static <Dto,Model> IPage<Dto> convert(IPage<Model> modelIPage, Function<List<Model>,List<Dto>> convertFunction){
        Page<Dto> page = new Page<>();
        page.setCurrent(modelIPage.getCurrent());
        page.setSize(modelIPage.getSize());
        page.setRecords(convertFunction.apply(modelIPage.getRecords()));
        page.setTotal(modelIPage.getTotal());
        page.setPages(modelIPage.getPages());
        return page;
    }

}
