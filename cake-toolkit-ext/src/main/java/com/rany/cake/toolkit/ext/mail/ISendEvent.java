package com.rany.cake.toolkit.ext.mail;

/**
 * 发送信息接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2024/02/18 18:16
 */
public interface ISendEvent<T> {

    /**
     * 发送接口
     *
     * @param msg msg
     */
    void send(T msg);

}
