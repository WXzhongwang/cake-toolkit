package com.rany.cake.toolkit.lang.crypto;

import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/3 23:02
 */
public class Rc4Tests {

    @Test
    public void test() {
        RC4 r = new RC4("12443");
        String xxx = r.encrypt("xxx");
        System.out.println(xxx);
        System.out.println(r.decrypt(xxx));
    }

}
