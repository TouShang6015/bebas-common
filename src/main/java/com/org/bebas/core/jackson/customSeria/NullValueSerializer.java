package com.org.bebas.core.jackson.customSeria;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

/**
 * 定制化序列器空值处理
 *
 * @Author wuHao
 * @Date 2022/11/18 16:52
 * @Return
 */
public class NullValueSerializer extends JsonSerializer<Object> {

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        String fieldName = gen.getOutputContext().getCurrentName();
        Class<?> clazz = gen.getCurrentValue().getClass();
        if (Map.class.isAssignableFrom(clazz)) {
            gen.writeNull();
            return;
        }
        Class<?> fieldType;
        try {
            fieldType = gen.getCurrentValue().getClass().getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            throw new IOException(e);
        }
        if (String.class.isAssignableFrom(fieldType)) {
            gen.writeString("");
            return;
        }
        if (Collection.class.isAssignableFrom(fieldType)) {
            gen.writeStartArray();
            gen.writeEndArray();
            return;
        }
        if (Boolean.class.isAssignableFrom(fieldType)) {
            gen.writeBoolean(false);
            return;
        }
        if (Map.class.isAssignableFrom(fieldType)) {
            gen.writeStartObject();
            gen.writeEndObject();
            return;
        }

        // 其它还是null
        gen.writeNull();
    }
}
