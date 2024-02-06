package com.rany.cake.toolkit.lang.cache;

import org.junit.Assert;
import org.junit.Test;

/**
 * LocalDataStoreTest
 *
 * @author zhongwang
 * @date 2024/2/5
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class LocalDataStoreTest {

    @Test
    public void test() {
        LocalDataStore localDataStore = new LocalDataStore();
        localDataStore.put("key1", "value1");
        localDataStore.put("key2", "value2");
        String value1 = (String) localDataStore.get("key1");
        String value2 = (String) localDataStore.get("key2");
        Assert.assertEquals("value1", value1);
        Assert.assertEquals("value2", value2);
        localDataStore.clear();
        localDataStore.forceClean();
        localDataStore.deleteFile();
    }
}