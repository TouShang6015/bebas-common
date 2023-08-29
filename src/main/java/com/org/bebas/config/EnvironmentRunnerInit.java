package com.org.bebas.config;

import com.org.bebas.config.global.PropertiesConfig;
import com.org.bebas.utils.logs.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wyj
 * @date 2022/8/19 15:15
 */
@Slf4j
@Component
public class EnvironmentRunnerInit implements InitializingBean {

    @Autowired
    private Environment environment;

    @Autowired
    private List<PropertiesConfig> propertiesConfigList;

    @Override
    public void afterPropertiesSet() throws Exception {
        LogUtil.consoleInfo(log, "- Bebas 配置加载", () -> {
            propertiesConfigList.forEach(item -> item.run(environment));
        });
    }
}
