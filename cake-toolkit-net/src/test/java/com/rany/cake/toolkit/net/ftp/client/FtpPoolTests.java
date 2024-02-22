package com.rany.cake.toolkit.net.ftp.client;

import com.rany.cake.toolkit.lang.Threads;
import com.rany.cake.toolkit.net.ftp.client.instance.IFtpInstance;
import com.rany.cake.toolkit.net.ftp.client.pool.FtpClientFactory;
import com.rany.cake.toolkit.net.ftp.client.pool.FtpClientPool;
import com.rany.cake.toolkit.net.ftp.config.FtpConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/13 1:43
 */
public class FtpPoolTests {

    private FtpClientPool pool;

    @Before
    public void init() {
        FtpConfig config = new FtpConfig("127.0.0.1").auth("yuanjinxiu", "131400");
        FtpClientFactory f = new FtpClientFactory(config);
        this.pool = new FtpClientPool(f, 3);
    }

    @Test
    public void get1() {
        pool.noAvailableThenCreate(true);
        System.out.println(pool.getClient());
        System.out.println(pool.getClient());
        System.out.println(pool.getClient());
        System.out.println(pool.getClient());
    }

    @Test
    public void get2() {
        System.out.println(pool.getClient());
        System.out.println(pool.getClient());
        System.out.println(pool.getClient());
        System.out.println(pool.getClient());
    }

    @Test
    public void get3() {
        IFtpInstance i;
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        Threads.sleep(1000);

        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
    }

    @Test
    public void get4() {
        IFtpInstance i;
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        i = pool.getInstance();
        System.out.println(i.getClient());
        i.close();
        Threads.sleep(1000);

        i = pool.getInstance();
        System.out.println(i.getClient());
        i = pool.getInstance();
        System.out.println(i.getClient());
        i = pool.getInstance();
        System.out.println(i.getClient());
        i = pool.getInstance();
        System.out.println(i.getClient());
    }

    @After
    public void destroy() throws InterruptedException {
        pool.close();
    }

}
