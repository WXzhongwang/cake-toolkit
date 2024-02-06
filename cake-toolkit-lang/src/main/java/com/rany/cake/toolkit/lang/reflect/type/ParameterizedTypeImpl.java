package com.rany.cake.toolkit.lang.reflect.type;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * 参数类型实现
 * <p>
 * from FastJSON 1.2.70
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2023/3/10 13:52
 */
public class ParameterizedTypeImpl implements ParameterizedType {

    private final Type[] actualTypeArguments;

    private final Type ownerType;

    private final Type rawType;

    public ParameterizedTypeImpl(Type[] actualTypeArguments, Type ownerType, Type rawType) {
        this.actualTypeArguments = actualTypeArguments;
        this.ownerType = ownerType;
        this.rawType = rawType;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return actualTypeArguments;
    }

    @Override
    public Type getOwnerType() {
        return ownerType;
    }

    @Override
    public Type getRawType() {
        return rawType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParameterizedTypeImpl that = (ParameterizedTypeImpl) o;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(actualTypeArguments, that.actualTypeArguments)) {
            return false;
        }
        if (ownerType != null ? !ownerType.equals(that.ownerType) : that.ownerType != null) {
            return false;
        }
        return rawType != null ? rawType.equals(that.rawType) : that.rawType == null;
    }

    @Override
    public int hashCode() {
        int result = actualTypeArguments != null ? Arrays.hashCode(actualTypeArguments) : 0;
        result = 31 * result + (ownerType != null ? ownerType.hashCode() : 0);
        result = 31 * result + (rawType != null ? rawType.hashCode() : 0);
        return result;
    }

}
