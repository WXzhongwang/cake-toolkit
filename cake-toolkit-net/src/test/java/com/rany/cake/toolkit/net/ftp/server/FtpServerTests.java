package com.rany.cake.toolkit.net.ftp.server;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/10 18:26
 */
public class FtpServerTests {

    public static void main(String[] args) {
        FtpServerInstance instance = new FtpServerInstance("127.0.0.1", 8800);
        FtpUser user = new FtpUser();
        user.setUsername("yuanjinxiu");
        user.setPassword("131400");
        user.setHomePath("/Users/yuanjinxiu/ftp");
        user.setWritePermission(true);
        instance.addUser(user)
                .listener()
                .start();
    }

}
