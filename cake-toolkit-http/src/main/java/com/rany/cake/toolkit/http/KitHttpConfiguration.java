package com.rany.cake.toolkit.http;


import com.rany.cake.toolkit.http.useragent.StandardUserAgent;
import com.rany.cake.toolkit.lang.config.KitConfig;

/**
 * orion-http 配置初始化器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2023/3/8 17:33
 */
public final class KitHttpConfiguration {

    public static final KitHttpConfiguration CONFIG = new KitHttpConfiguration();

    public final String HTTP_DEFAULT_USERAGENT = "http.default.useragent";

    static {
        KitConfig.init(CONFIG.HTTP_DEFAULT_USERAGENT, StandardUserAgent.CHROME_4);
    }

    private KitHttpConfiguration() {
    }

}
