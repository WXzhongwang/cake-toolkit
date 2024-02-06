package com.rany.cake.toolkit.lang.iterator;

import com.rany.cake.toolkit.lang.reflect.Classes;
import com.rany.cake.toolkit.lang.utils.Valid;

import java.io.Serializable;
import java.util.Iterator;

/**
 * 父类迭代器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/1/7 18:52
 */
public class ClassIterator<T> implements Iterator<Class<? super T>>, Iterable<Class<? super T>>, Serializable {

    private final Class<T> clazz;

    private Class<? super T> current;

    /**
     * 是否包含 Object.class
     */
    private final boolean includeObject;

    public ClassIterator(Class<T> clazz) {
        this(clazz, false);
    }

    public ClassIterator(Class<T> clazz, boolean includeObject) {
        Valid.notNull(clazz, "class is null");
        this.current = this.clazz = clazz;
        this.includeObject = includeObject;
    }

    @Override
    public boolean hasNext() {
        if (clazz.getName().contains(Classes.CGLIB_CLASS_SEPARATOR)) {
            return false;
        }
        this.current = current.getSuperclass();
        if (current == null) {
            return false;
        }
        return includeObject || !Object.class.equals(current);
    }

    /**
     * @return 父类
     */
    @Override
    public Class<? super T> next() {
        return current;
    }

    @Override
    public Iterator<Class<? super T>> iterator() {
        return this;
    }

}
