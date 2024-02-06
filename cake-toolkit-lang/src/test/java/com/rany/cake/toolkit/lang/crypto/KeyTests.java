package com.rany.cake.toolkit.lang.crypto;

import com.rany.cake.toolkit.lang.crypto.enums.CipherAlgorithm;
import com.rany.cake.toolkit.lang.wrapper.Pair;
import org.junit.Ignore;
import org.junit.Test;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/9/29 11:48
 */
public class KeyTests {

    @Test
    @Ignore
    public void test1() {
        String k1 = Keys.getKey("/Users/zhongshengwang/Desktop/key/rsa_public.pem");
        String k2 = Keys.getKey("/Users/zhongshengwang/Desktop/key/rsa_private_pkcs8.pem");
        PublicKey k3 = Keys.getCerPublicKey("/Users/zhongshengwang/Desktop/key/openssl.cer");
        Pair<PublicKey, PrivateKey> keys = Keys.getPfxKeys("/Users/zhongshengwang/Desktop/key/openssl.pfx", "123456");
        PublicKey k4 = keys.getKey();
        PrivateKey k5 = keys.getValue();
        System.out.println("k1 = " + k1);
        System.out.println("k2 = " + k2);
        System.out.println("k3 = " + k3);
        System.out.println("k4 = " + k4);
        System.out.println("k5 = " + k5);
    }

    @Test
    public void aesKey() {
        SecretKey g1 = Keys.generatorKey("qa", CipherAlgorithm.AES);
        SecretKey g2 = Keys.generatorKey("qa", 128, CipherAlgorithm.AES);
        SecretKey g3 = Keys.generatorKey("qa", 192, CipherAlgorithm.AES);
        SecretKey g4 = Keys.generatorKey("qa", 256, CipherAlgorithm.AES);
        System.out.println("g1 = " + g1);
        System.out.println("g2 = " + g2);
        System.out.println("g3 = " + g3);
        System.out.println("g4 = " + g4);
    }

    @Test
    public void desKey() {
        SecretKey g1 = Keys.generatorKey("qa", CipherAlgorithm.DES);
        SecretKey g2 = Keys.generatorKey("qa", 9000, CipherAlgorithm.DES);
        SecretKey g3 = Keys.generatorKey("qa", 192, CipherAlgorithm.DES);
        SecretKey g4 = Keys.generatorKey("qa", 256, CipherAlgorithm.DES);
        System.out.println("g1 = " + g1);
        System.out.println("g2 = " + g2);
        System.out.println("g3 = " + g3);
        System.out.println("g4 = " + g4);
    }

    @Test
    public void des3Key() {
        SecretKey g1 = Keys.generatorKey("qa", CipherAlgorithm.DES3);
        SecretKey g2 = Keys.generatorKey("qa", 9000, CipherAlgorithm.DES3);
        SecretKey g3 = Keys.generatorKey("qa", 192, CipherAlgorithm.DES3);
        SecretKey g4 = Keys.generatorKey("qa", 256, CipherAlgorithm.DES3);
        System.out.println("g1 = " + g1);
        System.out.println("g2 = " + g2);
        System.out.println("g3 = " + g3);
        System.out.println("g4 = " + g4);
    }

    @Test
    public void rsaKey() {
        System.out.println(RSA.generatorKeys(512));
        System.out.println(RSA.generatorKeys(1024));
    }

}
