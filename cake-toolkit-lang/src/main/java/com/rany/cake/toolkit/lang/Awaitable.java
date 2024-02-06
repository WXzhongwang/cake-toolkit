package com.rany.cake.toolkit.lang;

/**
 * 同步执行接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/4/17 13:40
 */
public interface Awaitable<T> {

    /**
     * 同步执行
     *
     * @return 执行结果
     */
    T await();

}
