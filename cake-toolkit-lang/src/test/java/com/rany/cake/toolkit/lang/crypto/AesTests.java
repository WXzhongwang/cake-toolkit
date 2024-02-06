package com.rany.cake.toolkit.lang.crypto;

import com.rany.cake.toolkit.lang.crypto.enums.CipherAlgorithm;
import com.rany.cake.toolkit.lang.crypto.enums.WorkingMode;
import com.rany.cake.toolkit.lang.crypto.symmetric.ParamSymmetric;
import com.rany.cake.toolkit.lang.crypto.symmetric.SymmetricBuilder;
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
public class AesTests {

    private String s = "123";
    private String k = "123";

    @Test
    public void test() {
        ParamSymmetric sy = SymmetricBuilder.aes()
                .workingMode(WorkingMode.CFB)
                .ivSpec("1234567812345678")
                .generatorSecretKey(k)
                .buildParam();
        String s1 = sy.encryptAsString(s);
        String d1 = sy.decryptAsString(s1);
        System.out.println(s1);
        System.out.println(d1);
        System.out.println(sy.verify(d1, s1));
    }

    @Test
    public void ecb() {
        String e1 = AES.encrypt(s, k);
        String d1 = AES.decrypt(e1, k);
        System.out.println("e1 = " + e1);
        System.out.println("d1 = " + d1);
    }

    @Test
    public void cbc() {
        String s1 = AES.encrypt(s, k, "1234567812345678");
        String d1 = AES.decrypt(s1, k, "1234567812345678");
        System.out.println("s1 = " + s1);
        System.out.println("d1 = " + d1);
    }

    @Test
    public void gcm() {
        String gcm = Strings.repeat('1', 96);
        String s1 = AES.encrypt(s, k, gcm, "aad");
        String d1 = AES.decrypt(s1, k, gcm, "aad");
        System.out.println("s1 = " + s1);
        System.out.println("d1 = " + d1);
    }

    @Test
    public void test1() {
        SecretKey key = Keys.generatorKey("key", CipherAlgorithm.AES);
        for (int i = 0; i < 1000; i++) {
            String s = Strings.randomChars(Randoms.randomInt(5, 20));
            String enc = AES.encrypt(s, key);
            System.out.println(enc);
            String dec = AES.decrypt(enc, key);
            System.out.println(dec);
            Valid.isTrue(s.equals(dec));
        }
    }

    @Test
    public void testAes() {
        for (int i = 0; i < 5000; i++) {
            String val = Strings.randomChars(Randoms.randomInt(30));
            String key = Randoms.randomAscii(Randoms.randomInt(30));
            String en = AES.encrypt(val, key);
            String de = AES.decrypt(en, key);
            Valid.isTrue(val.equals(de));
        }
    }

}
