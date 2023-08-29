package com.org.bebas.core.function;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Runnable 增强类
 *
 * @author WuHao
 * @date 2022/5/21 10:52
 */
public class OpenRunnable {

    /**
     * flag为true时，执行runnable
     *
     * @param flag     状态
     * @param runnable runnable
     */
    public static void run(boolean flag, SingleFunction runnable) {
        if (flag) runnable.run();
    }

    /**
     * 根据条件执行runnable
     *
     * @param arg1
     * @param predicate
     * @param runnable
     */
    public static <T> void run(T arg1, Predicate<T> predicate, SingleFunction runnable) {
        if (predicate.test(arg1)) runnable.run();
    }

    /**
     * 根据条件执行consumer
     *
     * @param arg1
     * @param predicate
     * @param consumer
     */
    public static <T> void run(T arg1, Predicate<T> predicate, Consumer<T> consumer) {
        if (predicate.test(arg1)) consumer.accept(arg1);
    }

}
