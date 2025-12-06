package com.org.bebas.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author wyj
 * @since 2025/12/6 16:26
 */
@FunctionalInterface
public interface FunctionSerializable<T, R> extends Function<T, R>, Serializable {
}
