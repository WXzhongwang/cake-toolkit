package com.rany.cake.toolkit.rule;

/**
 * @Author jiaoyang
 * @Version 1.0
 */
public abstract class Const<T> implements RElement {

    /**
     * 获取值信息
     *
     * @return 值
     */
    public abstract Object getValue();

    /**
     * 返回值类型
     *
     * @return 返回值类型
     */
    public abstract VarDataType getReturnType();


}
