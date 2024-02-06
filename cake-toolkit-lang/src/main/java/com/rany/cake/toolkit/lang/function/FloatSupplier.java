package com.rany.cake.toolkit.lang.function;

/**
 * float supplier
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/1/22 21:55
 */
@FunctionalInterface
public interface FloatSupplier {

    /**
     * 获取 float 值
     *
     * @return float
     */
    float getAsFloat();

}
