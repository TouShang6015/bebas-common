package com.org.bebas.constants;

/**
 * redis常量
 *
 * @author WuHao
 * @since 2022/5/13 9:46
 */
public interface RedisConstant {

    String REDIS_LOCK = "LOCK:";

    interface NameSpace {
        String MODULE_DATA = "MODULE-DATA:";

        String TABLE = "TABLE:";

    }

    interface Keyword {
        String TOKEN_LOGIN = "TOKEN_LOGIN:";

        String ALL_LIST = "ALL-LIST:";

        String LIST_PARAM = "LIST:";

        String ID = "ID:";
    }

}
