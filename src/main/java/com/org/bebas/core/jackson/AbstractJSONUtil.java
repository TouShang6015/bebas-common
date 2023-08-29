package com.org.bebas.core.jackson;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.org.bebas.core.jackson.customSeria.NullValueSerializer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * @author wyj
 * @date 2022/11/18 16:13
 */
public abstract class AbstractJSONUtil<B> {

    protected ObjectMapper objectMapper;

    public AbstractJSONUtil() {
        this.objectMapper = new Jackson2ObjectMapperBuilder().createXmlMapper(false).build();
        this.setConfigure(mapper -> {
            //对象的所有字段全部列入
            objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
            //取消默认转换timestamps形式
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            //忽略空Bean转json的错误
            objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            //所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
            objectMapper.setDateFormat(new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN));
            //忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况。防止错误
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        });
    }

    public AbstractJSONUtil(JsonSerializer<Object> serializer) {
        this();
        if (Objects.nonNull(serializer)) {
            objectMapper.getSerializerProvider().setNullValueSerializer(serializer);
        }
    }

    /**
     * 获取ObjectMapper对象
     *
     * @return
     */
    public ObjectMapper getMapper() {
        return this.objectMapper;
    }

    /**
     * 设置mapper配置信息
     *
     * @param objectMapperConsumer
     */
    public void setConfigure(Consumer<ObjectMapper> objectMapperConsumer) {
        objectMapperConsumer.accept(objectMapper);
    }

    /**
     * Object 转换成 JSON字符串
     *
     * @param obj
     * @return
     */
    public String toJSONString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("jackson exception! Object convert toJSONString fail!");
        }
    }

    /**
     * 开启 null赋予默认值
     *
     * @return
     */
    public B openNullDefault() {
        this.setConfigure(om -> om.getSerializerProvider().setNullValueSerializer(new NullValueSerializer()));
        return (B) this;
    }

}
