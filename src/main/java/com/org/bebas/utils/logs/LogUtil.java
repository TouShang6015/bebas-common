package com.org.bebas.utils.logs;

import com.org.bebas.core.function.SingleFunction;
import io.vavr.Tuple2;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;

import java.util.Arrays;

/**
 * 日志工具类
 *
 * @author WuHao
 * @since 2022/5/12 15:59
 */
public class LogUtil {

    public static void logConsole(SingleFunction... runs) {
        if (runs.length <= NumberUtils.INTEGER_ZERO)
            return;
        Arrays.stream(runs).forEach(SingleFunction::run);
    }

    /**
     * info 日志打印
     *
     * @param log
     * @param mainInfoMessage
     * @param tuples
     */
    public static void consoleInfo(Logger log, String mainInfoMessage, Tuple2<String, SingleFunction>... tuples) {
        if (tuples.length <= NumberUtils.INTEGER_ZERO)
            return;
        log.info(mainInfoMessage);
        Arrays.stream(tuples).forEach(t -> {
            log.info(t._1);
            t._2.run();
        });
    }

    /**
     * info日志打印
     *
     * @param log
     * @param logInfo
     * @param runs
     */
    public static void consoleInfo(Logger log, String logInfo, SingleFunction... runs) {
        if (runs.length <= NumberUtils.INTEGER_ZERO)
            return;
        log.info(logInfo);
        Arrays.stream(runs).forEach(SingleFunction::run);
    }

    public static String getBlock(Object msg) {
        if (msg == null) {
            msg = "";
        }
        return "[" + msg.toString() + "]";
    }

}
