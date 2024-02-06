package com.rany.cake.toolkit.lang.cache;

import org.junit.Test;

/**
 * LruCacheTest
 *
 * @author zhongwang
 * @date 2024/2/5
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class SoftCacheTest {

    @Test
    public void test() {
        SoftCache<String, Object> cache = new SoftCache<>(true);
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);
        cache.put("4", 4);
        cache.put("5", 5);
        cache.put("6", 6);

        cache.get("1");
        cache.get("null");
        cache.get("null");
        cache.get("null");
        cache.get("null");
        cache.get("null");

        double hitsRate = cache.getHitsRate();
        System.out.println(hitsRate);
    }
}
