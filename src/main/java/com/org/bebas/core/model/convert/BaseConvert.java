package com.org.bebas.core.model.convert;

import java.util.List;

/**
 * @author WuHao
 * @since 2023/3/24 10:29
 */
public interface BaseConvert<Model, DTO> {

    /**
     * dto 转换 model
     *
     * @param dto
     * @return
     */
    Model convertToModel(DTO dto);

    /**
     * dtoList 转换 modelList
     *
     * @param dto
     * @return
     */
    List<Model> convertToModel(List<DTO> dto);

    /**
     * model 转换 dto
     *
     * @param model
     * @return
     */
    DTO convertToDTO(Model model);

    /**
     * modelList 转换 dtoList
     *
     * @param model
     * @return
     */
    List<DTO> convertToDTO(List<Model> model);


}
