package com.org.bebas.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author WuHao
 * @date 2022/5/12 16:58
 */
public interface CharsetConstant {

    /**
     * ISO-8859-1
     */
    String ISO_8859_1 = "ISO-8859-1";
    /**
     * UTF-8
     */
    String UTF_8 = "UTF-8";
    /**
     * GBK
     */
    String GBK = "GBK";

    /**
     * ISO-8859-1
     */
    Charset CHARSET_ISO_8859_1 = StandardCharsets.ISO_8859_1;
    /**
     * UTF-8
     */
    Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;
    /**
     * GBK
     */
    Charset CHARSET_GBK = Charset.forName(GBK);

}
