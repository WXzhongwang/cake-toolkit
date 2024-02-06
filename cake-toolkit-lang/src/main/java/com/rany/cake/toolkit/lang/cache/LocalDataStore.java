package com.rany.cake.toolkit.lang.cache;


import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Strings;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * 将对象存储到本地
 * 对象必须实现序列化接口
 * 如果解析不存在的对象会为null, 但是数据存在
 * getMap 不可以用于操作元素, 如果操作注意序列化问题, 以及使用 save() 存储
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2019/8/22 14:41
 */
public class LocalDataStore {

    /**
     * 本地文件
     */
    private final File localDataStoreFile;

    /**
     * 数据容器
     */
    private Map<Object, Object> localStore;

    /**
     * 默认store
     */
    public static final LocalDataStore STORE = new LocalDataStore();

    public LocalDataStore() {
        this(new File(Files1.getPath(System.getProperty("user.dir") + "/data/dataStore.map")));
    }

    public LocalDataStore(File file) {
        this.localDataStoreFile = file;
        this.init();
    }

    /**
     * 初始化
     */
    @SuppressWarnings("unchecked")
    private void init() {
        Files1.touch(localDataStoreFile);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(localDataStoreFile));
            localStore = (Map<Object, Object>) in.readObject();
        } catch (Exception e) {
            localStore = new HashMap<>(Const.CAPACITY_8);
        } finally {
            Streams.close(in);
        }
    }

    /**
     * 删除文件
     */
    public boolean deleteFile() {
        return localDataStoreFile.delete();
    }

    /**
     * 插入元素
     */
    public void put(Serializable key, Serializable value) {
        localStore.put(key, value);
        this.write();
    }

    /**
     * 插入元素
     *
     * @param map 集合
     */
    public void putAll(Map<? extends Serializable, ? extends Serializable> map) {
        localStore.putAll(map);
        this.write();
    }

    /**
     * 获取元素
     *
     * @param key key
     * @return 元素
     */
    public Object get(Object key) {
        return localStore.get(key);
    }

    /**
     * 获取元素容器
     * 不能插入
     *
     * @return 元素容器
     */
    public Map<Object, Object> getMap() {
        return localStore;
    }

    /**
     * 删除元素
     *
     * @param key key
     */
    public void remove(Object key) {
        localStore.remove(key);
        this.write();
    }

    /**
     * 大小
     */
    public int size() {
        return localStore.size();
    }

    /**
     * 清空
     */
    public void clear() {
        localStore.clear();
        this.write();
    }

    /**
     * 持久化
     */
    public void save() {
        this.write();
    }

    /**
     * 写入
     */
    private void write() {
        if (localStore != null) {
            this.forceClean();
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(Files.newOutputStream(localDataStoreFile.toPath()));
                out.writeObject(localStore);
                out.writeObject(null);
            } catch (Exception e) {
                throw Exceptions.ioRuntime(e);
            } finally {
                Streams.close(out);
            }
        }
    }

    /**
     * 清空文件数据
     */
    public void forceClean() {
        BufferedWriter clean = null;
        try {
            clean = new BufferedWriter(new FileWriter(localDataStoreFile));
            clean.write(Strings.EMPTY);
            clean.flush();
        } catch (Exception e) {
            throw Exceptions.ioRuntime(e);
        } finally {
            Streams.close(clean);
        }
    }

    @Override
    public String toString() {
        return localStore.toString();
    }

}
