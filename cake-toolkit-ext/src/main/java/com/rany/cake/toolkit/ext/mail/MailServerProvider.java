package com.rany.cake.toolkit.ext.mail;

import java.io.Serializable;

/**
 * SMTP 提供者
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2024/02/18 18:16
 */
public interface MailServerProvider extends Serializable {

    /**
     * 获取服务主机
     *
     * @return host
     */
    String getHost();

    /**
     * 获取服务端口
     *
     * @return port
     */
    int getPort();

}
