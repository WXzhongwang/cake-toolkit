package com.rany.cake.toolkit.net.ftp.client;

import com.alibaba.fastjson.JSON;
import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.time.Dates;
import com.rany.cake.toolkit.lang.utils.Lists;
import com.rany.cake.toolkit.net.base.file.transfer.IFileTransfer;
import com.rany.cake.toolkit.net.ftp.client.instance.IFtpInstance;
import com.rany.cake.toolkit.net.ftp.client.pool.FtpClientFactory;
import com.rany.cake.toolkit.net.ftp.config.FtpConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/11 11:36
 */
public class FtpClintTests {

    private IFtpInstance e;

    @Before
    public void init() {
        FtpConfig config = new FtpConfig("127.0.0.1", 8800).auth("yuanjinxiu", "131400");
        FtpClientFactory f = new FtpClientFactory(config);
        this.e = f.createInstance();
    }

    @Test
    public void testGetFile() {
        e.touch("/workspace/1.txt");
        e.touch("/workspace/2.txt");
        System.out.println(JSON.toJSONString(e.getFile("/workspace/1.txt")));
        System.out.println(JSON.toJSONString(e.listFiles("/workspace")));
    }

    @Test
    public void testSize() {
        System.out.println(e.getSize("/workspace/1.txt"));
        System.out.println(e.getSize("/workspace/2.txt"));
    }

    @Test
    public void testExist() {
        e.mkdirs("/workspace2");
        e.change("/workspace2");
        System.out.println(e.isExist("/1.txt"));
        System.out.println(e.isExist("/workspace/1.txt"));
        System.out.println(e.isExist("/workspace/2.txt"));
        System.out.println(e.isExist("/root/bbb/ccc/cct.txt"));
    }

    @Test
    public void testModify() {
        String path = "/workspace/3.txt";
        e.touch(path);
        System.out.println(e.getWorkDirectory());
        System.out.println(Dates.format(new Date(e.getModifyTime(path))));
        e.setModifyTime(path, Dates.build(2020, 2, 2, 2, 2, 2));
        System.out.println(Dates.format(new Date(e.getModifyTime(path))));
    }

    @Test
    public void testTouch() {
        e.touch("/root/test24/1.txt");
        e.touch("/root/test24/2.txt");
        e.touch("/root/test24/3.txt");
        e.touch("/root/test24/3.txt");
    }

    @Test
    public void testMkdir() {
        e.mkdirs("/root/test22/t/4/5");
        e.mkdirs("/root/test22/t/4/5");
        e.mkdirs("/root/test22/t/4/6");
        e.mkdirs("/root/test22/t/4/7");
        e.mkdirs("/root/test22/t/4/8");
    }

    @Test
    public void testRm() {
        e.touch("/root/test1x/1.txt");
        e.touch("/root/test2x/1.txt");
        e.touch("/root/test2x/2.txt");
        e.touch("/root/test2x/d/1.txt");
        e.removeFile("/root/test2x/1.txt");
        e.removeDir("/root/test1x");
        e.rm("/root/test2x");
    }

    @Test
    public void testMove() {
        String path1 = "/root/test1x/1/1.txt";
        String path2 = "/root/test2x/1/2.txt";
        String path3 = "/root/test2x/1/3.txt";
        String path4 = "/root/4.txt";
        e.touch(path1);
        System.out.println(JSON.toJSONString(e.getFile(path1)));
        e.mv(path1, path2);
        System.out.println(JSON.toJSONString(e.getFile(path2)));
        e.mv(path2, "3.txt");
        System.out.println(JSON.toJSONString(e.getFile(path3)));
        e.mv(path3, "../../4.txt");
        System.out.println(JSON.toJSONString(e.getFile(path4)));
    }

    @Test
    public void testList() {
        System.out.println(e.listFiles("/root", false));
        System.out.println("---------");
        System.out.println(e.listFilesSuffix("/root", ".txt", false));
        System.out.println("---------");
        System.out.println(e.listFilesMatch("/root", "4.", false));
        System.out.println("---------");
        System.out.println(e.listFilesPattern("/root", Pattern.compile(".*\\.txt"), false));
        System.out.println("---------");
        System.out.println(e.listFilesFilter("/root", s -> s.getSize() > 20, false));
        System.out.println("---------");
        System.out.println(e.listDirs("/root", false));
    }

    @Test
    public void testRead() throws IOException {
        String path = "/root/1.txt";
        byte[] bs;
        int read;

        bs = new byte[20];
        read = e.read(path, bs);
        System.out.println(read);
        System.out.println(new String(bs, 0, read));
        System.out.println("------");

        bs = new byte[20];
        read = e.read(path, bs, 0, 2);
        System.out.println(read);
        System.out.println(new String(bs, 0, read));
        System.out.println("------");

        bs = new byte[20];
        read = e.read(path, 5, bs);
        System.out.println(read);
        System.out.println(new String(bs, 0, read));
        System.out.println("------");

        bs = new byte[20];
        read = e.read(path, 5, bs, 0, 2);
        System.out.println(read);
        System.out.println(new String(bs, 0, read));
        System.out.println("------");
    }

    @Test
    public void testReadLine() throws IOException {
        String path = "/root/4.txt";
        System.out.println(e.readLine(path));
        System.out.println("-----");

        System.out.println(e.readLine(path, 9));
        System.out.println("-----");

        System.out.println(e.readLine(path, 10));
        System.out.println("-----");

        System.out.println(e.readLine(path, 11));
        System.out.println("-----");

        System.out.println(e.readLines(path));
        System.out.println("-----");

        System.out.println(e.readLines(path, 2));
        System.out.println("-----");

        System.out.println(e.readLines(path, 3));
        System.out.println("-----");

        System.out.println(e.readLines(path, 2L));
        System.out.println("-----");

        System.out.println(e.readLines(path, 3L));
        System.out.println("-----");

        System.out.println(e.readLines(path, 2L, 2));
        System.out.println("-----");

        System.out.println(e.readLines(path, 3L, 2));
        System.out.println("-----");

        System.out.println(e.readLines(path, 10L, 2));
        System.out.println("-----");
    }

    @Test
    public void testTransfer() throws IOException {
        String path = "/root/1.txt";

        e.transfer(path, System.out);
        System.out.println("-----");

        e.transfer(path, System.out, 3, 5);
        System.out.println("-----");

        e.transfer(path, System.out, 3);
    }

    @Test
    public void testWrite() throws IOException {
        String path = "/root/write/1.txt";
        e.write(path, "111".getBytes());
        e.transfer(path, System.out);
        Threads.sleep(500);

        System.out.println("----");
        e.write(path, "123\n".getBytes());
        e.transfer(path, System.out);
        Threads.sleep(500);

        System.out.println("----");
        e.write(path, Streams.toInputStream("123123123123\n"));
        e.transfer(path, System.out);
        Threads.sleep(500);
    }

    @Test
    public void testWriteLine() throws IOException {
        String path = "/root/write/1.txt";

        e.writeLine(path, "111");
        e.transfer(path, System.out);
        Threads.sleep(500);

        System.out.println("----");
        e.writeLines(path, Lists.of("111", "我是", "333", "444"));
        e.transfer(path, System.out);
        Threads.sleep(500);
    }

    @Test
    public void testAppend() throws IOException {
        String path = "/root/write/12.txt";
        e.truncate(path);

        e.append(path, "111".getBytes());
        e.transfer(path, System.out);
        Threads.sleep(500);

        System.out.println("----");
        e.append(path, "123\n".getBytes());
        e.transfer(path, System.out);
        Threads.sleep(500);

        System.out.println("----");
        e.append(path, Streams.toInputStream("123123123123\n"));
        e.transfer(path, System.out);
        Threads.sleep(500);
    }

    @Test
    public void testAppendLine() throws IOException {
        String path = "/root/write/1.txt";
        e.truncate(path);

        e.appendLine(path, "111");
        e.transfer(path, System.out);
        Threads.sleep(500);

        System.out.println("----");
        e.appendLines(path, Lists.of("111", "我是", "333", "444"));
        e.transfer(path, System.out);
        Threads.sleep(500);
    }

    @Test
    public void testUpload() throws IOException {
        e.uploadFile("/build.sh", "/Users/yuanjinxiu/build.sh");
        e.uploadDir("/logs", "/Users/yuanjinxiu/logs", true);
    }

    @Test
    public void testDownload() throws IOException {
        e.downloadFile("/build.sh", "/Users/yuanjinxiu/build-new.sh");
        e.downloadDir("/logs", "/Users/yuanjinxiu/logs-new", true);
    }

    @Test
    public void testBigUpload() {
        String local = "/Users/yuanjinxiu/Downloads/ppt模板.zip";
        System.out.println(Files1.md5(local));

        IFileTransfer u = e.upload("/root/ppt模板.zip", local);
        u.getProgress()
                .computeRate()
                .rateAcceptor(pr -> {
                    System.out.println(pr.getProgress() * 100 + "% " + pr.getNowRate() / 1024 + "kb/s");
                })
                .callback(() -> {
                    System.out.println("done");
                    System.exit(0);
                });
        new Thread(u).start();
        // Threads.sleep(2000L);
        // u.abort();
        Threads.sleep(30000000L);
    }

    @Test
    public void testBigDownload() {
        String local = "/Users/yuanjinxiu/Downloads/ppt模板.zip";

        IFileTransfer u = e.download("/ppt模板.zip", local);
        u.getProgress()
                .computeRate()
                .rateAcceptor(pr -> {
                    System.out.println(pr.getProgress() * 100 + "% " + pr.getNowRate() / 1024 + "kb/s");
                })
                .callback(() -> {
                    System.out.println("done");
                    System.out.println(Files1.md5(local));
                    System.exit(1000);
                });
        new Thread(u).start();
        // Threads.sleep(2000L);
        // u.abort();
        Threads.sleep(30000000L);
    }

    @After
    public void destroy() {
        e.close();
    }

}
