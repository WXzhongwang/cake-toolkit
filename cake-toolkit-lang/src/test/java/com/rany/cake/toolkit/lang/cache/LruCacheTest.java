package com.rany.cake.toolkit.lang.cache;

import org.junit.Test;

/**
 * LruCacheTest
 *
 * @author zhongwang
 * @date 2024/2/5
 * @slogan Why Not
 * @email zhongshengwang.zsw@alibaba-inc.com
 */
public class LruCacheTest {

    @Test
    public void test() {
        LruCache<String, Object> cache = new LruCache<>();
        cache.put("1", 1);
        cache.put("2", 2);
        cache.put("3", 3);

        cache.get("1");
        cache.remove("2");
    }
}
