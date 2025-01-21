package com.rany.cake.toolkit.cms.client;

import com.rany.cake.toolkit.cms.exception.CmsException;
import com.rany.cake.toolkit.cms.util.CmsHttpClient;
import com.rany.cake.toolkit.cms.util.EnvEnum;
import com.rany.cake.toolkit.cms.util.FileUtil;
import com.rany.cake.toolkit.cms.util.RefreshThread;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class CmsClient {

    public static final String CMS_CLIENT = "cms";

    public String ossBucket = "http://rany-ops.oss-cn-hangzhou.aliyuncs.com";

    /**
     * 应用名
     */
    private String appName;

    /**
     * 是否是生产环境
     */
    private String env;

    /**
     * cms存放文件的oss地址
     */
    private String endpoint;

    /**
     * cms 磁盘根路径
     */
    private Path cmsPathRoot;

    /**
     * 用于发送http请求
     */
    private CmsHttpClient httpClient;

    /**
     * 内存缓存
     */
    private CacheManager cacheManager;

    /**
     * 定时任务，从比对oss和缓存中的数据是否一致
     */
    private ScheduledExecutorService executorService;

    /**
     * http client 初始化方法
     */
    public void init() {

        if (appName == null || env == null) {
            throw new CmsException("appName or env is null");
        }

        initPathRoot();

        initCache();

        initHttpClient();

        initEndpoint();

        initScheduleTask();

    }

    private void initPathRoot() {
        cmsPathRoot = Paths.get(System.getProperty("user.home"), CMS_CLIENT);
        try {
            FileUtil.deleteCacheFiles(cmsPathRoot);
        } catch (IOException ignore) {
        }
    }

    private void initCache() {
        Cache cache = new Cache(CMS_CLIENT,
                1000,
                false,
                false,
                60,
                0);
        cacheManager = CacheManager.create();
        cacheManager.addCache(cache);
    }

    private void initHttpClient() {
        // 设置连接管理器
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        // 设置连接池大小

        connManager.setMaxTotal(100);
        connManager.setDefaultMaxPerRoute(connManager.getMaxTotal());

        httpClient = new CmsHttpClient(
                HttpClientBuilder.create()
                        .setConnectionManager(connManager)
                        .build());
    }

    private void initEndpoint() {
        if (ArrayUtils.contains(EnvEnum.prod.value, env)) {

            endpoint = ossBucket + "/fe-res/production/";

        } else if (ArrayUtils.contains(EnvEnum.pre.value, env)) {

            endpoint = ossBucket + "/fe-res/pre/";

        } else {

            endpoint = ossBucket + "/fe-res/test/";
        }
    }

    private void initScheduleTask() {
        executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("cms-schedule-pool-%d").daemon(true).build());

        RefreshThread thread = new RefreshThread(cacheManager, endpoint, httpClient, cmsPathRoot);

        executorService.scheduleAtFixedRate(thread, 10, 15, TimeUnit.SECONDS);
    }

    public void destroy() {
        cacheManager.shutdown();
        executorService.shutdown();
        try {
            httpClient.destroy();
        } catch (IOException e) {
            throw new CmsException("关闭httpClient失败", e);
        }
    }

    /**
     * 加载资源文件，不同环境对应不同的加载策略
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    public String load(String fileName) {
        if (ArrayUtils.contains(EnvEnum.prod.value, env)) {
            // 生产环境
            return loadProdFile(fileName);
        } else if (ArrayUtils.contains(EnvEnum.pre.value, env)) {
            // 预发环境，暂时从oss中拉取
            return pullFromOss(fileName);
        } else {
            // 日常开发环境，直接从oss中拉取, 减少资源更新导致的延迟，避免开发体验过差以及一些开发不太方便感知的问题
            return pullFromOss(fileName);
        }
    }

    /**
     * 加载生产环境的文件
     *
     * @param fileName
     * @return
     */
    private String loadProdFile(String fileName) {
        String data = readFromCache(fileName);
        if (data != null) {
            return data;
        }
        data = FileUtil.readFromFile(cmsPathRoot.toString(), fileName);
        if (data == null) {
            //避免向同一个fileName发起多个请求
            synchronized (fileName.intern()) {
                data = readFromCache(fileName);
                if (data == null) {
                    data = httpClient.doGet(endpoint + fileName);
                    if (data == null) {
                        return null;
                    }
                    writeToCache(fileName, data);
                    FileUtil.writeToFile(cmsPathRoot, fileName, data);
                }
            }
        } else {
            writeToCache(fileName, data);
        }
        return data;

    }

    /**
     * 从缓存中读取
     *
     * @param fileName 文件名
     * @return 文件内容
     */
    private String readFromCache(String fileName) {
        Element element = cacheManager.getCache(CMS_CLIENT).get(fileName);
        return element != null ? element.getObjectValue().toString() : null;
    }

    /**
     * 将data放入缓存中
     *
     * @param fileName key
     * @param data     数据
     */
    private void writeToCache(String fileName, String data) {
        Element element = new Element(fileName, data);
        cacheManager.getCache(CMS_CLIENT).put(element);
    }

    /**
     * 从oss中获取文件
     *
     * @param fileName 读取文件内容
     * @return
     */
    private String pullFromOss(String fileName) {
        return httpClient.doGet(endpoint + fileName);
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setOssBucket(String ossBucket) {
        this.ossBucket = ossBucket;
    }
}