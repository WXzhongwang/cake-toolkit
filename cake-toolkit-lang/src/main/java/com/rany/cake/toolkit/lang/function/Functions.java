package com.rany.cake.toolkit.lang.function;

import com.rany.cake.toolkit.lang.Console;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 函数常量
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/2/20 18:50
 */
public class Functions {

    private Functions() {
    }

    /**
     * 空实现的 Consumer
     */
    public static <T> Consumer<T> emptyConsumer() {
        return t -> {
        };
    }

    /**
     * 打印的 Consumer
     */
    public static <T> Consumer<T> printConsumer() {
        return Console::trace;
    }

    /**
     * 空实现 BiConsumer
     */
    public static <T, U> BiConsumer<T, U> emptyBiConsumer() {
        return (t, u) -> {
        };
    }

}
