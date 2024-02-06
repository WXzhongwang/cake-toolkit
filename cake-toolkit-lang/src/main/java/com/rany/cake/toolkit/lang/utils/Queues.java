package com.rany.cake.toolkit.lang.utils;


import com.rany.cake.toolkit.lang.collect.FixedDeque;
import com.rany.cake.toolkit.lang.collect.FixedQueue;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Queue 工具类
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/11/6 11:38
 */
public class Queues extends Collections {

    private Queues() {
    }

    // -------------------- new --------------------

    public static <E> Queue<E> newQueue() {
        return new ConcurrentLinkedQueue<>();
    }

    public static <E> Queue<E> newQueue(Collection<E> c) {
        return new ConcurrentLinkedQueue<>(c);
    }

    public static <E> Deque<E> newDeque() {
        return new ConcurrentLinkedDeque<>();
    }

    public static <E> Deque<E> newDeque(Collection<E> c) {
        return new ConcurrentLinkedDeque<>(c);
    }

    public static <E> Queue<E> newLimitQueue(int size) {
        return new FixedQueue<>(size);
    }

    public static <E> Deque<E> newLimitDeque(int size) {
        return new FixedDeque<>(size);
    }

    // -------------------- function --------------------

    public static <E> Queue<E> def(Queue<E> q) {
        return q == null ? new ConcurrentLinkedQueue<>() : q;
    }

    public static <E> Deque<E> def(Deque<E> d) {
        return d == null ? new ConcurrentLinkedDeque<>() : d;
    }

    public static <E> Queue<E> def(Queue<E> q, Queue<E> def) {
        return q == null ? def : q;
    }

    public static <E> Deque<E> def(Deque<E> d, Deque<E> def) {
        return d == null ? def : d;
    }

    public static <E> Queue<E> def(Queue<E> q, Supplier<Queue<E>> def) {
        return q == null ? def.get() : q;
    }

    public static <E> Deque<E> def(Deque<E> d, Supplier<Deque<E>> def) {
        return d == null ? def.get() : d;
    }

    @SafeVarargs
    public static <E> Queue<E> of(E... e) {
        return new ConcurrentLinkedQueue<>(Arrays.asList(e));
    }

    @SafeVarargs
    public static <E> Deque<E> ofd(E... e) {
        return new ConcurrentLinkedDeque<>(Arrays.asList(e));
    }

    @SafeVarargs
    public static <E, V> Queue<E> of(Function<V, E> f, V... e) {
        Valid.notNull(f, "convert function is null");
        Queue<E> q = new ConcurrentLinkedQueue<>();
        int length = Arrays1.length(e);
        for (int i = 0; i < length; i++) {
            q.add(f.apply(e[i]));
        }
        return q;
    }

    @SafeVarargs
    public static <E, V> Deque<E> ofd(Function<V, E> f, V... e) {
        Valid.notNull(f, "convert function is null");
        Deque<E> q = new ConcurrentLinkedDeque<>();
        int length = Arrays1.length(e);
        for (int i = 0; i < length; i++) {
            q.add(f.apply(e[i]));
        }
        return q;
    }

    public static <E, V> Queue<E> map(Queue<V> l, Function<V, E> f) {
        Valid.notNull(f, "convert function is null");
        Queue<E> q = new ConcurrentLinkedQueue<>();
        if (isEmpty(l)) {
            return q;
        }
        for (V v : l) {
            q.add(f.apply(v));
        }
        return q;
    }

    public static <E, V> Deque<E> map(Deque<V> l, Function<V, E> f) {
        Valid.notNull(f, "convert function is null");
        Deque<E> q = new ConcurrentLinkedDeque<>();
        if (isEmpty(l)) {
            return q;
        }
        for (V v : l) {
            q.add(f.apply(v));
        }
        return q;
    }

    public static <E> Queue<E> as(Iterator<E> iterator) {
        Queue<E> q = new ConcurrentLinkedQueue<>();
        if (iterator != null) {
            while (iterator.hasNext()) {
                q.add(iterator.next());
            }
        }
        return q;
    }

    public static <E> Deque<E> asd(Iterator<E> iterator) {
        Deque<E> q = new ConcurrentLinkedDeque<>();
        if (iterator != null) {
            while (iterator.hasNext()) {
                q.add(iterator.next());
            }
        }
        return q;
    }

    public static <E> Queue<E> as(Enumeration<E> iterator) {
        Queue<E> q = new ConcurrentLinkedQueue<>();
        if (iterator != null) {
            while (iterator.hasMoreElements()) {
                q.add(iterator.nextElement());
            }
        }
        return q;
    }

    public static <E> Deque<E> asd(Enumeration<E> iterator) {
        Deque<E> q = new ConcurrentLinkedDeque<>();
        if (iterator != null) {
            while (iterator.hasMoreElements()) {
                q.add(iterator.nextElement());
            }
        }
        return q;
    }

}
