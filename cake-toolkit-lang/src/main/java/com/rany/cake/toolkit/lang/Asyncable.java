package com.rany.cake.toolkit.lang;

/**
 * 异步执行接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/4/17 13:41
 */
public interface Asyncable<T> {

    /**
     * 异步执行
     *
     * @param handler 异步处理器
     */
    void async(T handler);

}
