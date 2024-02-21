package com.rany.cake.toolkit.ext.tail.handler;


import com.rany.cake.toolkit.ext.tail.Tracker;

/**
 * 读取到行的操作
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/5/14 16:37
 */
@FunctionalInterface
public interface LineHandler {

    /**
     * 读取到行
     *
     * @param read    行
     * @param line    当前行
     * @param tracker 追踪器
     */
    void readLine(String read, int line, Tracker tracker);

}
