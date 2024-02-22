package com.rany.cake.toolkit.ext.watch.file.handler;


import com.rany.cake.toolkit.lang.io.FileAttribute;

import java.io.File;

/**
 * 空实现 事件处理器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/31 0:31
 */
public class DefaultEventHandler implements EventHandler {

    @Override
    public void onAccess(File file, FileAttribute before, FileAttribute current) {
    }

    @Override
    public void onModified(File file, FileAttribute before, FileAttribute current) {
    }

    @Override
    public void onCreate(File file, FileAttribute current) {
    }

    @Override
    public void onDelete(File file, FileAttribute before) {
    }

}
