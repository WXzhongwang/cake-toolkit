package com.rany.cake.toolkit.http.ok.ws.handler;

import okhttp3.WebSocket;

/**
 * 关闭连接调用
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/4/9 21:57
 */
@FunctionalInterface
public interface CloseHandler {

    /**
     * 关闭连接处理
     *
     * @param webSocket webSocket
     * @param code      code
     * @param reason    reason
     */
    void close(WebSocket webSocket, int code, String reason);

}
