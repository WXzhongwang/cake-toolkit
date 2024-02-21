package com.rany.cake.toolkit.ext.tail.handler;


import com.rany.cake.toolkit.ext.tail.Tracker;

/**
 * 读取到数据的操作
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/6/16 12:12
 */
@FunctionalInterface
public interface DataHandler {

    /**
     * 读取到数据
     *
     * @param bytes   bytes
     * @param len     读取的长度
     * @param tracker 追踪器
     */
    void read(byte[] bytes, int len, Tracker tracker);

}
