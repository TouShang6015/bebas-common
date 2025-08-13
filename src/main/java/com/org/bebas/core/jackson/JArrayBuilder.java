package com.org.bebas.core.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.List;

/**
 * @author WuHao
 * @since 2022/11/18 16:10
 */
public class JArrayBuilder extends AbstractJSONUtil<JArrayBuilder> {

    public JArrayBuilder() {
        super(null);
    }

    @Override
    public String toJSONString(Object obj) {
        return super.toJSONString(obj);
    }

    public <T> List<T> parseArray(String jsonString, Class<T> cls) {

        Assert.notNull(jsonString, "jsonString is not null!");
        Assert.notNull(cls, "class param is not null!");
        try {
            return objectMapper.readValue(jsonString, new TypeReference<List<T>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("jackson exception! JsonString parseObject fail!");
        }
    }

}
