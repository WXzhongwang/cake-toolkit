package com.rany.cake.toolkit.net.socket;


import com.rany.cake.toolkit.lang.Threads;

import java.util.concurrent.Executors;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/10 11:01
 */
public class TcpReceiveTests {

    public static void main(String[] args) throws Exception {
        TcpReceive receive = new TcpReceive(7790);
        receive.acceptThreadPool(Executors.newCachedThreadPool());
        receive.accept(2);
        for (int i = 0; i < 10; i++) {
            Threads.sleep(1000);
            receive.sendAll("sd".getBytes());
        }
        System.out.println(receive.findSocket("127.0.0.1"));
        receive.close();
        receive.closePool();
    }

}
