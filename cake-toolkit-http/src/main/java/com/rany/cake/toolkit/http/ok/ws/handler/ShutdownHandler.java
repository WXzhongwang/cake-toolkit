package com.rany.cake.toolkit.http.ok.ws.handler;

import okhttp3.WebSocket;

import java.util.Map;

/**
 * 关闭Server接口
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/4/9 22:01
 */
@FunctionalInterface
public interface ShutdownHandler {

    /**
     * 关闭server处理
     *
     * @param clients webSockets
     */
    void shutdown(Map<String, WebSocket> clients);

}
