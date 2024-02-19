package com.rany.cake.toolkit.redis;

import org.redisson.api.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisUtils {

    private final RedissonClient redissonClient;

    public RedisUtils(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }


    public void set(String key, String value) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        bucket.set(value);
    }

    public String get(String key) {
        RBucket<String> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }


    public void lPush(String key, String... values) {
        RList<String> list = redissonClient.getList(key);
        list.addAll(java.util.Arrays.asList(values));
    }

    public List<String> lRange(String key, int start, int end) {
        RList<String> list = redissonClient.getList(key);
        return list.range(start, end);
    }

    public void lTrim(String key, int start, int end) {
        RList<String> list = redissonClient.getList(key);
        list.trim(start, end);
    }

    public void lRemove(String key, String value) {
        RList<String> list = redissonClient.getList(key);
        list.removeAll(java.util.Arrays.asList(value));
    }


    public void hSet(String key, String field, String value) {
        RMap<String, String> map = redissonClient.getMap(key);
        map.put(field, value);
    }

    public String hGet(String key, String field) {
        RMap<String, String> map = redissonClient.getMap(key);
        return map.get(field);
    }

    public Map<String, String> hGetAll(String key) {
        RMap<String, String> map = redissonClient.getMap(key);
        return map.readAllMap();
    }

    public void hDel(String key, String field) {
        RMap<String, String> map = redissonClient.getMap(key);
        map.remove(field);
    }


    public void sAdd(String key, String... members) {
        RSet<String> set = redissonClient.getSet(key);
        set.addAll(java.util.Arrays.asList(members));
    }

    public Set<String> sMembers(String key) {
        RSet<String> set = redissonClient.getSet(key);
        return set.readAll();
    }

    public boolean sIsMember(String key, String member) {
        RSet<String> set = redissonClient.getSet(key);
        return set.contains(member);
    }

    public void sRem(String key, String... members) {
        RSet<String> set = redissonClient.getSet(key);
        set.removeAll(java.util.Arrays.asList(members));
        // 可以考虑使用异步的方法
        // set.removeAllAsync(Arrays.asList(members));
    }

    public void zAdd(String key, double score, String member) {
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(key);
        sortedSet.add(score, member);
    }

    public Collection<String> zRange(String key, int start, int end) {
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(key);
        return sortedSet.valueRange(start, end);
    }

    public void zRem(String key, String... members) {
        RScoredSortedSet<String> sortedSet = redissonClient.getScoredSortedSet(key);
        sortedSet.removeAll(java.util.Arrays.asList(members));
    }
}
