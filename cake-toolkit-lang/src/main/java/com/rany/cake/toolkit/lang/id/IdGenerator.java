package com.rany.cake.toolkit.lang.id;

/**
 * id 生成器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/7/11 17:23
 */
public interface IdGenerator<T> {

    /**
     * 获取下一个id
     *
     * @return id
     */
    T nextId();

}
