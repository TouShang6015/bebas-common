package com.org.bebas.core.jackson;

import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author wyj
 * @date 2022/11/18 16:10
 */
public class JObjectBuilder extends AbstractJSONUtil<JObjectBuilder> {

    private JsonSerializer<Object> jsonSerializer;

    public JObjectBuilder() {
        super(null);
    }

    @Override
    public String toJSONString(Object obj) {
        return super.toJSONString(obj);
    }

    public <T> T parseObject(String jsonString, Class<T> cls) {
        Assert.notNull(jsonString, "jsonString is not null!");
        Assert.notNull(cls, "class param is not null!");
        try {
            return objectMapper.readValue(jsonString, cls);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("jackson exception! JsonString parseObject fail!");
        }
    }

}
