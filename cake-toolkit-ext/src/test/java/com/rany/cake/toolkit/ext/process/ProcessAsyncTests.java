package com.rany.cake.toolkit.ext.process;

import org.junit.Test;

import java.io.File;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/10/12 17:40
 */
public class ProcessAsyncTests {

    @Test
    public void echo() {
        try (ProcessAsyncExecutor exec = new ProcessAsyncExecutor("echo $JAVA_HOME")
                .outputFile(new File("/Users/yuanjinxiu/Desktop/r1.txt"))) {
            exec.terminal()
                    .exec();
        } finally {

        }
    }

    @Test
    public void sql() {
        try (ProcessAsyncExecutor exec = new ProcessAsyncExecutor("mysql -u root -pZsw131400.")
                .outputFile(new File("/Users/yuanjinxiu/Desktop/r1.txt"))
                .inputFile(new File("/Users/yuanjinxiu/Desktop/r2.txt"))) {
            exec.terminal()
                    .redirectError()
                    .exec();
        } finally {

        }
    }

    @Test
    public void bat() {
        try (ProcessAsyncExecutor exec = new ProcessAsyncExecutor("/Users/yuanjinxiu/1.bat")
                .outputFile(new File("/Users/yuanjinxiu/r1.txt"))) {
            exec.exec();
        } finally {

        }
    }

}
