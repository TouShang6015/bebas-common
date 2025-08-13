package com.org.bebas.config.global;

import org.springframework.core.env.Environment;

/**
 * @author WuHao
 * @description:
 * @since 2023/4/23 13:56
 * @Version 1.0
 */
public interface PropertiesConfig {

    void run(Environment environment);

}
