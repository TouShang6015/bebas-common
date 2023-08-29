package com.org.bebas.core.jackson;

/**
 * @author wyj
 * @date 2022/11/18 15:42
 */
public class JSON {

    private JSON() {
    }

    public static JObjectBuilder buildObject() {
        return new JObjectBuilder();
    }

    public static JArrayBuilder buildArray() {
        return new JArrayBuilder();
    }
}
