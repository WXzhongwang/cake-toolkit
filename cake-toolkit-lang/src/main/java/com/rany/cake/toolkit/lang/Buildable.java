package com.rany.cake.toolkit.lang;

/**
 * 构建接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2019/11/18 18:09
 */
public interface Buildable<T> {

    /**
     * 构建
     *
     * @return T
     */
    T build();

}
