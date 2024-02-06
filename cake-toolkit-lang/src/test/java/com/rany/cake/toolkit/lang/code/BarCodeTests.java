package com.rany.cake.toolkit.lang.code;

import com.rany.cake.toolkit.lang.awt.Images;
import com.rany.cake.toolkit.lang.codec.Base64s;
import com.rany.cake.toolkit.lang.io.FileWriters;
import com.rany.cake.toolkit.lang.utils.Strings;
import org.junit.Test;

import java.awt.*;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/4/14 11:37
 */
public class BarCodeTests {

    @Test
    public void test0() {
        BarCodes c = new BarCodes();
        String s = c.encodeBase64("www");
        System.out.println(s);
        String d = c.decodeBase64(s);
        byte[] bytes = Images.base64Decode(s);
        FileWriters.writeFast("/Users/zhongshengwang/条形码.png", bytes);
        System.out.println(d);
    }

    @Test
    public void test1() {
        BarCodes c = new BarCodes();
        String s = c.encodeBase64("www");
        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);

        byte[] keyFileData = Base64s.decode(Strings.bytes(s));
        FileWriters.writeFast("~/Users/zhongshengwang/1.jpg", keyFileData);
    }

    @Test
    public void test2() {
        BarCodes c = new BarCodes();
        String s = c.encodeBase64("www", "www");
        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);
    }

    @Test
    public void test3() {
        BarCodes c = new BarCodes();
        c.fontColor(Color.BLUE);
        c.width(500);
        String s = c.encodeBase64("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh", "哈哈哈");

        System.out.println(s);
        String d = c.decodeBase64(s);
        System.out.println(d);
    }

}
