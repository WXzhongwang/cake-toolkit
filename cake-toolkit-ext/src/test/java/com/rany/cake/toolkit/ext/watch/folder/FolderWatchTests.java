package com.rany.cake.toolkit.ext.watch.folder;


import com.rany.cake.toolkit.ext.watch.folder.handler.DefaultWatchHandler;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/31 15:15
 */
public class FolderWatchTests {

    public static void main(String[] args) {
        BlockFolderWatcher watcher = new BlockFolderWatcher(new DefaultWatchHandler() {
            @Override
            public void onCreate(WatchEvent<?> event, Path path) {
                System.out.println("create " + path + " " + event.kind() + " " + event.count() + " " + event.context());
            }

            @Override
            public void onModify(WatchEvent<?> event, Path path) {
                System.out.println("modify " + path + " " + event.kind() + " " + event.count() + " " + event.context());
            }

            @Override
            public void onDelete(WatchEvent<?> event, Path path) {
                System.out.println("delete " + path + " " + event.kind() + " " + event.count() + " " + event.context());
            }

            @Override
            public void onOverflow(WatchEvent<?> event, Path path) {
                System.out.println("overflow " + path + " " + event.kind() + " " + event.count() + " " + event.context());
            }
        }, WatchEventKind.ALL);
        watcher.registerPath("/Users/yuanjinxiu/Desktop/k1");
        watcher.run();

    }

}
