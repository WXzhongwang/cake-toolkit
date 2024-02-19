package com.rany.cake.toolkit.ext.mail;


import com.rany.cake.toolkit.ext.KitExtConfiguration;
import com.rany.cake.toolkit.lang.config.KitConfig;

/**
 * SMTP 邮件服务器类型
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2024/02/18 18:16
 */
public enum MailServerType implements MailServerProvider {

    /**
     * 网易
     */
    WY163("smtp.163.com", 465),

    /**
     * QQ
     */
    QQ("smtp.qq.com", 465),

    /**
     * 移动
     */
    YD139("smtp.139.com", 465),

    /**
     * 自定义
     */
    CUSTOMER(KitConfig.get(KitExtConfiguration.CONFIG.MAIL_CUSTOMER_HOST),
            KitConfig.get(KitExtConfiguration.CONFIG.MAIL_CUSTOMER_PORT)),
    ;

    private final String host;

    private final Integer port;

    MailServerType(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

}

