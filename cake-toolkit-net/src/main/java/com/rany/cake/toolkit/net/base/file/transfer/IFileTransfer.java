package com.rany.cake.toolkit.net.base.file.transfer;


import com.rany.cake.toolkit.lang.io.SafeCloseable;
import com.rany.cake.toolkit.lang.progress.ByteTransferRateProgress;

/**
 * 文件传输器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/5/19 9:56
 */
public interface IFileTransfer extends Runnable, SafeCloseable {

    /**
     * 终止传输
     */
    void abort();

    /**
     * 获取传输进度条
     *
     * @return 传输进度条
     */
    ByteTransferRateProgress getProgress();

}
