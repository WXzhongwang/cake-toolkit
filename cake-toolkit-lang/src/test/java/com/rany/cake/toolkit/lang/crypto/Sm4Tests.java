package com.rany.cake.toolkit.lang.crypto;

import com.rany.cake.toolkit.lang.crypto.enums.CipherAlgorithm;
import com.rany.cake.toolkit.lang.random.Randoms;
import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.lang.utils.Valid;
import org.junit.Test;

import javax.crypto.SecretKey;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/9/29 11:48
 */
public class Sm4Tests {

    private String s = "123";
    private String k1 = "345";

    @Test
    public void ecb() {
        String e1 = SM4.encrypt(s, k1);
        String d1 = SM4.decrypt(e1, k1);
        System.out.println("e1 = " + e1);
        System.out.println("d1 = " + d1);
    }

    @Test
    public void cbc() {
        String s1 = SM4.encrypt(s, k1, "1234567812345678");
        String d1 = SM4.decrypt(s1, k1, "1234567812345678");
        System.out.println("s1 = " + s1);
        System.out.println("d1 = " + d1);
    }

    @Test
    public void test1() {
        SecretKey key = Keys.generatorKey("key", CipherAlgorithm.SM4);
        for (int i = 0; i < 1000; i++) {
            String s = Strings.randomChars(Randoms.randomInt(5, 20));
            String enc = SM4.encrypt(s, key);
            System.out.println(enc);
            String dec = SM4.decrypt(enc, key);
            System.out.println(dec);
            Valid.isTrue(s.equals(dec));
        }
    }

    @Test
    public void testSM4() {
        for (int i = 0; i < 5000; i++) {
            String val = Strings.randomChars(Randoms.randomInt(30));
            String key = Randoms.randomAscii(Randoms.randomInt(30));
            String en = SM4.encrypt(val, key);
            String de = SM4.decrypt(en, key);
            Valid.isTrue(val.equals(de));
        }
    }

}
