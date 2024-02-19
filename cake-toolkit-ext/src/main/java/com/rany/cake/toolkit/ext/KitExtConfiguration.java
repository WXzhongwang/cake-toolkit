package com.rany.cake.toolkit.ext;


import com.rany.cake.toolkit.lang.config.KitConfig;
import com.rany.cake.toolkit.lang.utils.Strings;

/**
 * orion-ext 配置初始化器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2024/02/18 18:16
 */
public final class KitExtConfiguration {

    public static final KitExtConfiguration CONFIG = new KitExtConfiguration();

    public final String LOCATION_UNKNOWN = "location.address.unknown";

    public final String MAIL_CUSTOMER_HOST = "mail.customer.host";

    public final String MAIL_CUSTOMER_PORT = "mail.customer.port";

    static {
        KitConfig.init(CONFIG.LOCATION_UNKNOWN, "未知");
        KitConfig.init(CONFIG.MAIL_CUSTOMER_HOST, Strings.EMPTY);
        KitConfig.init(CONFIG.MAIL_CUSTOMER_PORT, 465);
    }

    private KitExtConfiguration() {
    }

}
