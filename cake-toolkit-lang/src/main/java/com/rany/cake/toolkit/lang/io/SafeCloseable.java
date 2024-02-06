package com.rany.cake.toolkit.lang.io;

import java.io.Closeable;

/**
 * 安全关闭接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/22 13:46
 */
public interface SafeCloseable extends Closeable {

    /**
     * 安全关闭
     */
    @Override
    void close();

}
