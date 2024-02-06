package com.rany.cake.toolkit.lang.mutable;

/**
 * 可变接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/1/8 13:45
 */
public interface Mutable<T> {

    /**
     * 获取值
     *
     * @return t
     */
    T get();

    /**
     * 设置值
     *
     * @param t t
     */
    void set(T t);

}
