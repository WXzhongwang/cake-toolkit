package com.rany.cake.toolkit.lang.crypto;

import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/4 1:11
 */
public class CaesarTests {

    @Test
    public void test1() {
        Caesars caesars = new Caesars();
        String e = caesars.encrypt("kdqoijiwqo43");
        System.out.println(e);
        String d = caesars.decrypt(e);
        System.out.println(d);
    }

}
