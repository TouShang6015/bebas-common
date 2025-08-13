package com.org.bebas.config.global;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import com.org.bebas.core.function.OR;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author WuHao
 * @since 2022/5/11 19:20
 */
@ToString
@Slf4j
@Component
public class IdGenerateConfig implements PropertiesConfig {

    /**
     * 机器码,必须由外部设定，最大值 2^WorkerIdBitLength-1
     */
    private static short workerId = 0;
    /**
     * 雪花计算方法 （1-漂移算法|2-传统算法），默认1
     */
    private static short method = 1;
    /**
     * 基础时间（ms单位）,不能超过当前系统时间
     */
    private static long baseTime = 1218153600000L;
    /**
     * 机器码位长, 默认值6，取值范围 [1, 15]（要求：序列数位长+机器码位长不超过22）
     */
    private static byte workerIdBitLength = 8;
    /**
     * 序列数位长,默认值6，取值范围 [3, 21]（要求：序列数位长+机器码位长不超过22）
     */
    private static byte seqBitLength = 10;
    /**
     * 最大序列数（含）,设置范围 [MinSeqNumber, 2^SeqBitLength-1]，默认值0，表示最大序列数取最大值（2^SeqBitLength-1]）
     */
    private static short maxSeqNumber = 10;
    /**
     * 最小序列数（含）, 默认值5，取值范围 [5, MaxSeqNumber]，每毫秒的前5个序列数对应编号是0-4是保留位，其中1-4是时间回拨相应预留位，0是手工新值预留位
     */
    private static short minSeqNumber = 5;
    /**
     * 最大漂移次数（含）,默认2000，推荐范围500-10000（与计算能力有关）
     */
    private static short topOverCostCount = 3000;

    public static short getWorkerId() {
        return workerId;
    }

    public void setWorkerId(short workerId) {
        IdGenerateConfig.workerId = workerId;
    }

    public static short getMethod() {
        return method;
    }

    public void setMethod(short method) {
        IdGenerateConfig.method = method;
    }

    public static long getBaseTime() {
        return baseTime;
    }

    public void setBaseTime(long baseTime) {
        IdGenerateConfig.baseTime = baseTime;
    }

    public static byte getWorkerIdBitLength() {
        return workerIdBitLength;
    }

    public void setWorkerIdBitLength(byte workerIdBitLength) {
        IdGenerateConfig.workerIdBitLength = workerIdBitLength;
    }

    public static byte getSeqBitLength() {
        return seqBitLength;
    }

    public void setSeqBitLength(byte seqBitLength) {
        IdGenerateConfig.seqBitLength = seqBitLength;
    }

    public static short getMaxSeqNumber() {
        return maxSeqNumber;
    }

    public void setMaxSeqNumber(short maxSeqNumber) {
        IdGenerateConfig.maxSeqNumber = maxSeqNumber;
    }

    public static short getMinSeqNumber() {
        return minSeqNumber;
    }

    public void setMinSeqNumber(short minSeqNumber) {
        IdGenerateConfig.minSeqNumber = minSeqNumber;
    }

    public static short getTopOverCostCount() {
        return topOverCostCount;
    }

    public void setTopOverCostCount(short topOverCostCount) {
        IdGenerateConfig.topOverCostCount = topOverCostCount;
    }


    @Override
    public void run(Environment environment) {
        String prefix = "bebas.project.idGenerate.";
        IdGeneratorOptions idOptions = new IdGeneratorOptions();
        idOptions.WorkerId = workerId;
        idOptions.WorkerIdBitLength = workerIdBitLength;
        idOptions.BaseTime = baseTime;
        idOptions.TopOverCostCount = topOverCostCount;
        idOptions.Method = method;
        idOptions.MaxSeqNumber = maxSeqNumber;
        idOptions.SeqBitLength = seqBitLength;
        idOptions.MinSeqNumber = minSeqNumber;
        OR.run(environment.getProperty(prefix + "workerIdBitLength"), Objects::nonNull, val -> idOptions.WorkerIdBitLength = Byte.parseByte(val));
        OR.run(environment.getProperty(prefix + "baseTime"), Objects::nonNull, val -> idOptions.BaseTime = Long.parseLong(val));
        OR.run(environment.getProperty(prefix + "topOverCostCount"), Objects::nonNull, val -> idOptions.TopOverCostCount = Short.parseShort(val));
        OR.run(environment.getProperty(prefix + "method"), Objects::nonNull, val -> idOptions.Method = Short.parseShort(val));
        OR.run(environment.getProperty(prefix + "workerId"), Objects::nonNull, val -> idOptions.WorkerId = Short.parseShort(val));
        OR.run(environment.getProperty(prefix + "maxSeqNumber"), Objects::nonNull, val -> idOptions.MaxSeqNumber = Short.parseShort(val));
        OR.run(environment.getProperty(prefix + "seqBitLength"), Objects::nonNull, val -> idOptions.SeqBitLength = Byte.parseByte(val));
        OR.run(environment.getProperty(prefix + "minSeqNumber"), Objects::nonNull, val -> idOptions.MinSeqNumber = Short.parseShort(val));
        YitIdHelper.setIdGenerator(idOptions);
        log.info("{} {}", StringPool.DASH, "初始化snowId配置信息");
    }

}
