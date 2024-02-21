package com.rany.cake.toolkit.ext.tail;

import com.rany.cake.toolkit.ext.tail.delay.DelayTracker;
import com.rany.cake.toolkit.ext.tail.delay.DelayTrackerListener;
import com.rany.cake.toolkit.ext.tail.mode.FileMinusMode;
import com.rany.cake.toolkit.ext.tail.mode.FileNotFoundMode;
import com.rany.cake.toolkit.ext.tail.mode.FileOffsetMode;
import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.utils.Strings;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/5/14 16:07
 */
public class TailTests {

    @Test
    public void read1() {
        try (DelayTracker tracker = new DelayTracker("/Users/yuanjinxiu/Desktop/tail.txt", (s, l, t) -> {
            System.out.println(l + ": " + s);
        })) {
            tracker.offset(FileOffsetMode.LINE, 5)
                    .notFoundMode(FileNotFoundMode.WAIT)
                    .minusMode(FileMinusMode.CLOSE)
                    .tail();
        }

    }

    @Test
    public void read2() {
        OutputStream out = Files1.openOutputStreamSafe("/Users/yuanjinxiu/Desktop/merge.txt");
        DelayTrackerListener tracker = new DelayTrackerListener("/Users/yuanjinxiu/Desktop/tail.txt", (s, l, t) -> {
            try {
                if (l != -1) {
                    out.write(s, 0, l);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        tracker.offset(5).notFoundMode(FileNotFoundMode.WAIT)
                .minusMode(FileMinusMode.CLOSE)
                .tail();
    }

    @Test
    public void testAdd() throws IOException {
        try (FileOutputStream outputStream = Files1.openOutputStream(new File("/Users/yuanjinxiu/Desktop/tail.txt"), true)) {
            while (true) {
                outputStream.write(Strings.bytes(Strings.randomChars(10)));
                outputStream.write(13);
                Threads.sleep(1000);
            }
        }
    }

}
