package com.rany.cake.toolkit.net.remote.channel;

import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.lang.function.impl.ReaderLineConsumer;
import com.rany.cake.toolkit.net.remote.CommandExecutors;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/6 23:39
 */
public class CommandExecutorTests {

    private SessionStore s;

    @Before
    public void init() {
        this.s = SessionHolder.HOLDER.getSession("192.168.146.230", "root")
                .password("admin123")
                .timeout(20000)
                .connect(20000);
    }

    @Test
    public void ls() {
        SessionHolder.HOLDER.setLogger(SessionLogger.ERROR);
        CommandExecutor e = s.getCommandExecutor("ls -la /root");
        e.callback(() -> {
            System.out.println("end....");
            System.out.println(e.getExitCode());
            System.out.println(e.isSuccessExit());
            e.close();
        });
        e.streamHandler(ReaderLineConsumer.printer());
        e.errorStreamHandler(ReaderLineConsumer.printer());
        e.connect();
        e.exec();
        Threads.sleep(3000);
    }

    @Test
    public void echo() {
        SessionHolder.HOLDER.setLogger(SessionLogger.INFO);
        CommandExecutor e = s.getCommandExecutor("echo $PATH");
        e.inherit();
        e.streamHandler(ReaderLineConsumer.printer());
        e.callback(() -> {
            System.out.println("结束....");
            System.out.println(e.getExitCode());
            e.close();
        });
        e.connect();
        e.exec();
        Threads.sleep(3000);
    }

    @Test
    public void test1() throws IOException {
        CommandExecutor executor = s.getCommandExecutor("echo $JAVA_HOME");

        executor.inherit();
        executor.connect();

        System.out.println(CommandExecutors.getCommandOutputResultString(executor));

        System.out.println(executor.getExitCode());
        executor.close();
    }

    @Test
    public void test2() throws IOException {
        CommandExecutor executor = s.getCommandExecutor("/bin/bash -c 'echo $JAVA_HOME'");

        executor.inherit();
        executor.connect();

        System.out.println(CommandExecutors.getCommandOutputResultString(executor));

        System.out.println(executor.getExitCode());
        executor.close();
    }

    @Test
    public void test3() throws IOException {
        CommandExecutor executor = s.getCommandExecutor("ec111ho 1");

        executor.inherit();
        executor.connect();

        System.out.println(CommandExecutors.getCommandOutputResultString(executor));

        System.out.println(executor.getExitCode());
        executor.close();
    }

}
