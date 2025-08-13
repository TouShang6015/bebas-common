package com.org.bebas.config.global;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.org.bebas.core.function.OR;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author WuHao
 * @description:
 * @since 2023/4/23 14:38
 * @Version 1.0
 */
@ToString
@Slf4j
@Component
public class FutureUtilConfig implements PropertiesConfig {


    // 时间单位 毫秒
    public static final TimeUnit TIMEOUT_UNIT = TimeUnit.MILLISECONDS;

    /**
     * cpu核心数
     */
    private static int cpuProcessors = Runtime.getRuntime().availableProcessors();
    /**
     * 线程池中允许创建的最大线程数
     */
    private static int maximumPoolSize = 3 * cpuProcessors;
    /**
     * 线程执行超时时间
     */
    private static int timeoutValue = 1500;
    /**
     * 线程池中空闲线程等待工作的超时时间
     */
    private static int keepAliveTime = 3;
    /**
     * 线程池中队列大小
     */
    private static int dequeSize = 20;

    @Override
    public void run(Environment environment) {
        String prefix = "bebas.project.futureUtil.";
        OR.run(environment.getProperty(prefix + "cpuProcessors"), Objects::nonNull, val -> FutureUtilConfig.cpuProcessors = Integer.parseInt(val));
        OR.run(environment.getProperty(prefix + "maximumPoolSize"), Objects::nonNull, val -> FutureUtilConfig.maximumPoolSize = Integer.parseInt(val));
        OR.run(environment.getProperty(prefix + "timeoutValue"), Objects::nonNull, val -> FutureUtilConfig.timeoutValue = Integer.parseInt(val));
        OR.run(environment.getProperty(prefix + "keepAliveTime"), Objects::nonNull, val -> FutureUtilConfig.keepAliveTime = Integer.parseInt(val));
        OR.run(environment.getProperty(prefix + "dequeSize"), Objects::nonNull, val -> FutureUtilConfig.dequeSize = Integer.parseInt(val));
        log.info("{} {}", StringPool.DASH, "初始化FutureUtil配置信息");
    }

    public static int getCpuProcessors() {
        return cpuProcessors;
    }

    public static int getTimeoutValue() {
        return timeoutValue;
    }

    public static int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public static int getKeepAliveTime() {
        return keepAliveTime;
    }

    public static int getDequeSize() {
        return dequeSize;
    }
}
