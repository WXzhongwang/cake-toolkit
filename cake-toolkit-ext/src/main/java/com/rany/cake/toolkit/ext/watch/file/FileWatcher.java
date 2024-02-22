package com.rany.cake.toolkit.ext.watch.file;


import com.rany.cake.toolkit.lang.Stoppable;
import com.rany.cake.toolkit.lang.Watchable;

/**
 * 文件监听器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/28 16:40
 */
public abstract class FileWatcher implements Watchable, Runnable, Stoppable {

    @Override
    public void run() {
        watch();
    }

    /**
     * 是否为运行状态
     *
     * @return ignore
     */
    public abstract boolean isRun();

}
