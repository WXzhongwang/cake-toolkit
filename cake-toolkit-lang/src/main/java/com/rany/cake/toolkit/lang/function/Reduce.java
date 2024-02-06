package com.rany.cake.toolkit.lang.function;

/**
 * 规约接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2019/9/9 14:13
 */
@FunctionalInterface
public interface Reduce<V, R> {

    /**
     * 规约
     *
     * @param v input
     * @return output
     */
    R accept(V[] v);

}
