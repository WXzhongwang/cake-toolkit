package com.rany.cake.toolkit.ext.watch.folder;

import com.rany.cake.toolkit.ext.watch.folder.handler.WatchHandler;
import com.rany.cake.toolkit.lang.Stoppable;
import com.rany.cake.toolkit.lang.Watchable;
import com.rany.cake.toolkit.lang.io.SafeCloseable;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.utils.Arrays1;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Valid;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 文件夹监听器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/31 11:16
 */
public abstract class FolderWatcher implements Runnable, Watchable, Stoppable, SafeCloseable {

    protected WatchHandler handler;

    protected WatchEvent.Kind<?>[] kinds;

    protected WatchEvent.Modifier[] modifiers;

    protected volatile boolean run;

    protected WatchService watchService;

    protected Map<WatchKey, Path> watchKeys = new LinkedHashMap<>();

    protected FolderWatcher(WatchHandler handler, WatchEventKind... kinds) {
        this(handler, null, Arrays1.mapper(kinds, WatchEvent.Kind<?>[]::new, WatchEventKind::getValue));
    }

    protected FolderWatcher(WatchHandler handler, WatchEvent.Kind<?>... kinds) {
        this(handler, null, kinds);
    }

    protected FolderWatcher(WatchHandler handler, WatchEvent.Modifier[] modifiers, WatchEventKind... kinds) {
        this(handler, modifiers, Arrays1.mapper(kinds, WatchEvent.Kind<?>[]::new, WatchEventKind::getValue));
    }

    protected FolderWatcher(WatchHandler handler, WatchEvent.Modifier[] modifiers, WatchEvent.Kind<?>... kinds) {
        Valid.notNull(handler, "watch handler is null");
        this.handler = handler;
        this.modifiers = modifiers;
        this.kinds = Arrays1.def(kinds, WatchEventKind.ALL);
        this.init();
    }

    /**
     * 初始化
     */
    protected void init() {
        try {
            this.watchService = FileSystems.getDefault().newWatchService();
        } catch (IOException e) {
            throw Exceptions.watch(e);
        }
    }

    public void registerPath(String path) {
        this.registerPath(Paths.get(path), 1);
    }

    public void registerPath(File path) {
        this.registerPath(Paths.get(path.getAbsolutePath()), 1);
    }

    public void registerPath(URI path) {
        this.registerPath(Paths.get(path), 1);
    }

    public void registerPath(Path path) {
        this.registerPath(path, 1);
    }

    public void registerPath(String path, int maxDepth) {
        this.registerPath(Paths.get(path), maxDepth);
    }

    public void registerPath(File path, int maxDepth) {
        this.registerPath(Paths.get(path.getAbsolutePath()), maxDepth);
    }

    public void registerPath(URI path, int maxDepth) {
        this.registerPath(Paths.get(path), maxDepth);
    }

    /**
     * 注册监听的文件夹到 service
     * <p>
     * 如果文件夹不存在则创建
     * <p>
     * 可以监听到新的文件 但是监听不到新的文件夹
     *
     * @param path     path
     * @param maxDepth 递归子目录深度 =1不递归 >1递归 x - 1
     */
    public void registerPath(Path path, int maxDepth) {
        boolean exists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        boolean isDir = Files.isDirectory(path);
        if (!exists || !isDir) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw Exceptions.ioRuntime(e);
            }
        }
        try {
            WatchKey key;
            if (Arrays1.isEmpty(modifiers)) {
                key = path.register(watchService, kinds);
            } else {
                key = path.register(watchService, kinds, modifiers);
            }
            watchKeys.put(key, path);
            // 递归注册
            if (maxDepth > 1) {
                // 遍历子目录并加入监听
                Files.walkFileTree(path, EnumSet.noneOf(FileVisitOption.class), maxDepth, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        registerPath(dir, 0);
                        return super.postVisitDirectory(dir, exc);
                    }
                });
            }
        } catch (IOException e) {
            if (!(e instanceof AccessDeniedException)) {
                // 非禁止访问异常
                throw Exceptions.watch(e);
            }
        }
    }

    @Override
    public void run() {
        this.run = true;
        this.watch();
    }

    @Override
    public void stop() {
        this.run = false;
    }

    @Override
    public void close() {
        this.stop();
        Streams.close(watchService);
    }

    public Collection<Path> getWatchPaths() {
        return watchKeys.values();
    }

}
