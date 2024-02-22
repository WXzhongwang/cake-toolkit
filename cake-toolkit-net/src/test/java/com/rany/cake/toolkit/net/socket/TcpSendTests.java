package com.rany.cake.toolkit.net.socket;


import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Strings;

import java.io.IOException;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/10 11:02
 */
public class TcpSendTests {

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                try {
                    TcpSend send = new TcpSend("127.0.0.1", 7790);
                    while (true) {
                        String s = Strings.randomChars(5);
                        send.send(s.getBytes());
                        send.sendLf();
                        System.out.println("send: " + s);
                        send.flush();
                        Threads.sleep(1500);
                        System.out.println(send.getInput().available());
                    }
                } catch (IOException e) {
                    Exceptions.printStacks(e);
                }
            }).start();
        }
    }

}
