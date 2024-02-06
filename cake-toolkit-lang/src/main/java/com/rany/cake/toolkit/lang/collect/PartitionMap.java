package com.rany.cake.toolkit.lang.collect;


import com.rany.cake.toolkit.lang.math.Numbers;
import com.rany.cake.toolkit.lang.utils.Exceptions;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 分片 map
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2023/1/16 17:25
 */
public class PartitionMap<K, V> extends AbstractSet<Map<K, V>> implements Iterator<Map<K, V>> {

    private final Iterator<Map.Entry<K, V>> iterator;

    private final int size;

    private final int totalSize;

    private int current;

    public PartitionMap(Map<K, V> map, int size) {
        this.iterator = map.entrySet().iterator();
        this.size = size;
        this.totalSize = map.size();
    }

    @Override
    public int size() {
        return (totalSize + size - 1) / size;
    }

    @Override
    public boolean isEmpty() {
        return totalSize == 0;
    }

    @Override
    public Iterator<Map<K, V>> iterator() {
        return this;
    }

    @Override
    public boolean hasNext() {
        return this.size() > current;
    }

    @Override
    public Map<K, V> next() {
        if (!this.hasNext()) {
            throw Exceptions.noSuchElement("there are no more elements");
        }
        current++;
        Map<K, V> map = new HashMap<>(Numbers.getMin2Power(size));
        for (int i = 0; i < size; i++) {
            if (iterator.hasNext()) {
                Map.Entry<K, V> next = iterator.next();
                map.put(next.getKey(), next.getValue());
            } else {
                break;
            }
        }
        return map;
    }

}