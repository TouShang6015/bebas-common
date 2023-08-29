package com.org.bebas.core.json;

import com.alibaba.fastjson2.JSONObject;

/**
 * @author Wuhao
 * @date 2022/8/28 15:12
 */
public class JSONObjectBuilder {

    private final JSONObject jsonObject;

    public JSONObjectBuilder() {
        this.jsonObject = new JSONObject();
    }

    public static JSONObjectBuilder builder() {
        return new JSONObjectBuilder();
    }

    public JSONObjectBuilder put(String key, Object value) {
        this.jsonObject.put(key, value);
        return this;
    }

    public JSONObject build() {
        return this.jsonObject;
    }

}
