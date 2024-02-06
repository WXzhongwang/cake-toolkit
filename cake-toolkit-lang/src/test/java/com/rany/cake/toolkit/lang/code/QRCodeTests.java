package com.rany.cake.toolkit.lang.code;

import com.rany.cake.toolkit.lang.awt.Images;
import com.rany.cake.toolkit.lang.io.FileWriters;
import org.junit.Test;

import java.awt.*;
import java.io.File;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/4/14 10:31
 */
public class QRCodeTests {

    @Test
    public void test0() {
        QRCodes c = new QRCodes();
        String s = c.encodeBase64("www");
        System.out.println(s);

        byte[] bytes = Images.base64Decode(s);
        FileWriters.writeFast("/Users/zhongshengwang/二维码.png", bytes);

        String d = c.decodeBase64(s);
        System.out.println(d);

    }

    @Test
    public void test1() {
        QRCodes c = new QRCodes();
        String s = c.encodeBase64("www");
        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);
    }

    @Test
    public void test2() {
        QRCodes c = new QRCodes();
        String s = c.encodeBase64("www", "www");
        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);
    }

    @Test
    public void test3() {
        QRCodes c = new QRCodes();
        c.logo(new File("C:\\Users\\ljh15\\Pictures\\帽子\\1.jpg"));
        c.logoCompress();
        String s = c.encodeBase64("www");
        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);
    }

    @Test
    public void test4() {
        QRCodes c = new QRCodes();
        c.logo(new File("C:\\Users\\ljh15\\Pictures\\帽子\\1.jpg"));
        c.logoCompress();
        c.fontColor(Color.RED);
        String s = c.encodeBase64("哦哦哦哦哦!", "哦哦哦哦哦!");
        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);
    }

}
