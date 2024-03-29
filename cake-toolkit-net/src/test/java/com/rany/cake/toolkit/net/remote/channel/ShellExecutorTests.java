package com.rany.cake.toolkit.net.remote.channel;


import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.lang.function.impl.ReaderLineConsumer;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/7 20:36
 */
public class ShellExecutorTests {

    public static void main(String[] args) {
        SessionHolder.HOLDER.setLogger(SessionLogger.INFO);
        ShellExecutor e = SessionHolder.HOLDER.getSession("192.168.146.230", "root")
                .password("admin123")
                // ShellExecutor e = SessionHolder.getSession("192.168.146.230", "root")
                //         .setPassword("admin123")
                .timeout(20000)
                .connect(20000)
                .getShellExecutor();
        e.streamHandler(ReaderLineConsumer.printer());
        e.callback(() -> System.out.println("end...."));
        e.connect(20000);
        e.exec();
        e.write("ps -ef | grep java\n");
        e.write("ps -ef | grep ssh\n");
        e.write("ping www.baidu.com\n");
        // block
        e.write("ping www.jd.com\n");
        Threads.sleep(2000);
        System.out.println("--------------------------");
        System.out.println(e.isClosed());
        System.out.println(e.isConnected());
        System.out.println("--------------------------");
        e.interrupt();
        e.close();
        System.out.println(e.isClosed());
        System.out.println(e.isConnected());
        System.out.println("--------------------------");
        System.exit(0);
    }

}
