package com.rany.cake.toolkit.lang.function;

/**
 * byte supplier
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/1/22 21:49
 */
@FunctionalInterface
public interface ByteSupplier {

    /**
     * 获取 byte 值
     *
     * @return byte
     */
    byte getAsByte();

}
