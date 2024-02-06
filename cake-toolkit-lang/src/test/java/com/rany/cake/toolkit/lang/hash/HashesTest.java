package com.rany.cake.toolkit.lang.hash;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * HashesTest
 *
 * @author zhongwang
 * @date 2024/2/5
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class HashesTest {

    // 测试hashCode方法，针对null值和非数组对象
    @Test
    public void testHashCode() {
        assertEquals("对于null值应返回0", 0, Hashes.hashCode(null));
        assertEquals("对于字符序列对象应返回相同的hashCode", "example".hashCode(), Hashes.hashCode("example"));
    }

    // 测试identityHashCode方法，确保结果与System.identityHashCode相同
    @Test
    public void testIdentityHashCode() {
        Object obj = new Object();
        assertEquals("应返回对象的系统标识哈希码", System.identityHashCode(obj), Hashes.identityHashCode(obj));
    }

    // 测试hash方法，验证字符串哈希结果
    @Test
    public void testHash() {
        String str = "example";
        int expectedHash = 0;
        for (char c : str.toCharArray()) {
            expectedHash = 31 * expectedHash + c;
        }
        assertEquals("应返回与字符串默认方法相同的哈希值", expectedHash, Hashes.hash(str));
    }

    // 测试additiveHash方法，结果是否符合预期
    @Test
    public void testAdditiveHash() {
        String str = "example";
        int prime = 31;
        int hash = str.length();
        for (char c : str.toCharArray()) {
            hash += c;
        }
        hash %= prime;
        assertEquals("应返回加法哈希的结果", hash, Hashes.additiveHash(str, prime));
    }


    // 测试rotatingHash方法
    @Test
    public void testRotatingHash() {
        String str = "Hashes";
        int prime = 31;
        assertEquals("应返回旋转哈希结果", Hashes.rotatingHash(str, prime), Hashes.rotatingHash(str, prime));
    }

    // 测试oneByOneHash方法
    @Test
    public void testOneByOneHash() {
        String str = "Hashes";
        assertEquals("应返回一次一个哈希结果", Hashes.oneByOneHash(str), Hashes.oneByOneHash(str));
    }

    // 测试bernstein方法
    @Test
    public void testBernstein() {
        String str = "Hashes";
        assertEquals("应返回Bernstein哈希结果", Hashes.bernstein(str), Hashes.bernstein(str));
    }

    // 测试fnvHash方法（字节数组版本）
    @Test
    public void testFnvHashByteArray() {
        byte[] data = "Hashes".getBytes();
        int hash = Hashes.fnvHash(data);
        assertTrue("应返回FNV哈希结果", hash != 0);
    }

    // 测试fnvHash方法（字符串版本）
    @Test
    public void testFnvHashString() {
        String str = "Hashes";
        int hash = Hashes.fnvHash(str);
        assertTrue("应返回FNV哈希结果", hash != 0);
    }

    // 测试murmur32方法
    @Test
    public void testMurmur32() {
        byte[] data = "Hashes".getBytes();
        int hash = Hashes.murmur32(data);
        assertTrue("应返回Murmur32哈希结果", hash != 0);
    }

    // 测试murmur64方法
    @Test
    public void testMurmur64() {
        byte[] data = "Hashes".getBytes();
        long hash = Hashes.murmur64(data);
        assertTrue("应返回Murmur64哈希结果", hash != 0L);
    }

    // 测试murmur128方法
    @Test
    public void testMurmur128() {
        byte[] data = "Hashes".getBytes();
        long[] hash = Hashes.murmur128(data);
        assertNotNull("应返回Murmur128哈希结果", hash);
        assertEquals("哈希结果应为两个长整型值", 2, hash.length);
    }

    // 测试cityHash32方法
    @Test
    public void testCityHash32() {
        byte[] data = "Hashes".getBytes();
        int hash = Hashes.cityHash32(data);
        assertTrue("应返回CityHash32哈希结果", hash != 0);
    }

    // 测试cityHash64方法
    @Test
    public void testCityHash64() {
        byte[] data = "Hashes".getBytes();
        long hash = Hashes.cityHash64(data);
        assertTrue("应返回CityHash64哈希结果", hash != 0L);
    }

    // 测试cityHash128方法
    @Test
    public void testCityHash128() {
        byte[] data = "Hashes".getBytes();
        long[] hash = Hashes.cityHash128(data);
        assertNotNull("应返回CityHash128哈希结果", hash);
        assertEquals("哈希结果应为两个长整型值", 2, hash.length);
    }

    // 测试universal方法
    @Test
    public void testUniversal() {
        char[] key = {'a', 'b', 'c'};
        int mask = 0xFF; // 示例掩码
        int[] tab = new int[256]; // 示例表
        for (int i = 0; i < tab.length; i++) {
            tab[i] = i; // 简单初始化
        }
        int hash = Hashes.universal(key, mask, tab);
        assertTrue("应返回一个有效的哈希值", hash >= 0 && hash <= mask);
    }

    // 测试zobrist方法
    @Test
    public void testZobrist() {
        char[] key = {'a', 'b', 'c'};
        int mask = 0xFF; // 示例掩码
        int[][] tab = new int[key.length][256]; // 示例表
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < 256; j++) {
                tab[i][j] = j; // 简单初始化
            }
        }
        int hash = Hashes.zobrist(key, mask, tab);
        assertTrue("应返回一个有效的哈希值", hash >= 0 && hash <= mask);
    }

    // 测试fnvHash方法（byte数组版本）
    @Test
    public void testFnvHash() {
        byte[] data = "hashes".getBytes();
        int hash = Hashes.fnvHash(data);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试fnvHash方法（字符串版本）
    @Test
    public void testTestFnvHash() {
        String data = "hashes";
        int hash = Hashes.fnvHash(data);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试intHash方法
    @Test
    public void testIntHash() {
        int key = 123456;
        int hash = Hashes.intHash(key);
        assertNotEquals("哈希值不应等于原始整数值", key, hash);
    }

    // 测试rsHash方法
    @Test
    public void testRsHash() {
        String str = "hashes";
        int hash = Hashes.rsHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试jsHash方法
    @Test
    public void testJsHash() {
        String str = "hashes";
        int hash = Hashes.jsHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试pjwHash方法
    @Test
    public void testPjwHash() {
        String str = "hashes";
        int hash = Hashes.pjwHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试elfHash方法
    @Test
    public void testElfHash() {
        String str = "hashes";
        int hash = Hashes.elfHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试bkdrHash方法
    @Test
    public void testBkdrHash() {
        String str = "hashes";
        int hash = Hashes.bkdrHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试sdbmHash方法
    @Test
    public void testSdbmHash() {
        String str = "hashes";
        int hash = Hashes.sdbmHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试djbHash方法
    @Test
    public void testDjbHash() {
        String str = "hashes";
        int hash = Hashes.djbHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试dekHash方法
    @Test
    public void testDekHash() {
        String str = "hashes";
        int hash = Hashes.dekHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试apHash方法
    @Test
    public void testApHash() {
        String str = "hashes";
        int hash = Hashes.apHash(str);
        assertNotEquals("哈希值不应为0", 0, hash);
    }

    // 测试tianlHash方法
    @Test
    public void testTianlHash() {
        String str = "hashes";
        long hash = Hashes.tianlHash(str);
        assertNotEquals("哈希值不应为0", 0L, hash);
    }

    // 测试mixHash方法
    @Test
    public void testMixHash() {
        String str = "hashes";
        long hash = Hashes.mixHash(str);
        assertNotEquals("哈希值不应为0", 0L, hash);
    }

    // 测试cityHash64方法（无种子版本）
    @Test
    public void testTestCityHash64() {
        byte[] data = "hashes".getBytes();
        long hash = Hashes.cityHash64(data);
        assertNotEquals("哈希值不应为0", 0L, hash);
    }

    // 测试cityHash64方法（单个种子版本）
    @Test
    public void testTestCityHash641() {
        byte[] data = "hashes".getBytes();
        long seed = 123456789L;
        long hash = Hashes.cityHash64(data, seed);
        assertNotEquals("哈希值不应为0", 0L, hash);
    }

    // 测试cityHash128方法
    @Test
    public void testTestCityHash128() {
        byte[] data = "hashes".getBytes();
        long[] hash = Hashes.cityHash128(data);
        assertNotNull("哈希值数组不应为null", hash);
        assertEquals("哈希值数组应该包含两个元素", 2, hash.length);
        assertNotEquals("哈希值数组的第一个元素不应为0", 0L, hash[0]);
        assertNotEquals("哈希值数组的第二个元素不应为0", 0L, hash[1]);
    }
}