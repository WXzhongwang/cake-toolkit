package com.rany.cake.toolkit.cms.util;

import com.rany.cake.toolkit.cms.client.CmsClient;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.nio.file.Path;
import java.util.List;

public class RefreshThread implements Runnable {

    private final CacheManager cacheManager;

    private final String endpoint;

    private final CmsHttpClient httpClient;

    private final Path cmsPathRoot;

    public RefreshThread(CacheManager cacheManager, String endpoint, CmsHttpClient httpClient,
                         Path cmsPathRoot) {
        this.cacheManager = cacheManager;
        this.endpoint = endpoint;
        this.httpClient = httpClient;
        this.cmsPathRoot = cmsPathRoot;
    }

    @Override
    public void run() {
        Cache cache = cacheManager.getCache(CmsClient.CMS_CLIENT);
        List<String> keys = cache.getKeys();
        for (String key : keys) {
            Element element = cache.get(key);
            String data = httpClient.doGet(endpoint + key);
            // 文件不相等，刷新缓存和磁盘文件
            if (element != null) {
                if (!(element.getObjectValue().toString().equals(data))) {
                    refreshFile(cache, key, data);
                }
            }
        }
    }

    private void refreshFile(Cache cache, String key, String data) {
        Element newElement = new Element(key, data);
        cache.put(newElement);
        FileUtil.writeToFile(cmsPathRoot, key, data);
    }
}
