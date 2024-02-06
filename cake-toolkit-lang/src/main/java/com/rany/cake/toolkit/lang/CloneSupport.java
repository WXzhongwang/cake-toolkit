package com.rany.cake.toolkit.lang;

/**
 * 克隆
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/15 17:22
 */
public class CloneSupport<T> implements Cloneable {

    @SuppressWarnings("unchecked")
    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

}
