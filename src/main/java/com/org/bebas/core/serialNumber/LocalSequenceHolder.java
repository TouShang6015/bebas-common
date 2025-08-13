package com.org.bebas.core.serialNumber;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 本地序列号持有器
 *
 * @author wuhao
 * @since 2025-08-13 15:06:29
 */
public class LocalSequenceHolder {
    private static final Map<String, AtomicLong> sequenceMap = new ConcurrentHashMap<>();

    public static AtomicLong getSequence(String key) {
        return sequenceMap.computeIfAbsent(key, k -> new AtomicLong(0));
    }
}