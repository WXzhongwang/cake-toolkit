package com.rany.cake.toolkit.lang.crypto;


import com.rany.cake.toolkit.lang.io.Files1;
import org.junit.Test;

import java.io.File;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/9/29 9:58
 */
public class SignTests {

    @Test
    public void e() {
        String s = "123";
        System.out.println("Signatures.md5(s) = " + Signatures.md5(s));
        System.out.println("Signatures.md5(s, \"salt\") = " + Signatures.md5(s, "salt"));
        System.out.println("Signatures.md5(s, \"salt\", 3) = " + Signatures.md5(s, "salt", 3));
        System.out.println("Signatures.sha1(s) = " + Signatures.sha1(s));
        System.out.println("Signatures.sha224(s) = " + Signatures.sha224(s));
        System.out.println("Signatures.sha256(s) = " + Signatures.sha256(s));
        System.out.println("Signatures.sha384(s) = " + Signatures.sha384(s));
        System.out.println("Signatures.sha512(s) = " + Signatures.sha512(s));
        System.out.println();
        File f = new File("/Users/zhongshengwang/Desktop/key/bug.txt");
        System.out.println("Files1.md5(f) = " + Files1.md5(f));
        System.out.println("Files1.sha1(f) = " + Files1.sha1(f));
        System.out.println("Files1.sha224(f) = " + Files1.sha224(f));
        System.out.println("Files1.sha256(f) = " + Files1.sha256(f));
        System.out.println("Files1.sha384(f) = " + Files1.sha384(f));
        System.out.println("Files1.sha512(f) = " + Files1.sha512(f));
        System.out.println();
        System.out.println("Signatures.hmacMd5(\"1\", \"23\") = " + Signatures.hmacMd5("1", "23"));
        System.out.println("Signatures.hmacSha1(\"1\", \"23\") = " + Signatures.hmacSha1("1", "23"));
        System.out.println("Signatures.hmacSha224(\"1\", \"23\") = " + Signatures.hmacSha224("1", "23"));
        System.out.println("Signatures.hmacSha256(\"1\", \"23\") = " + Signatures.hmacSha256("1", "23"));
        System.out.println("Signatures.hmacSha384(\"1\", \"23\") = " + Signatures.hmacSha384("1", "23"));
        System.out.println("Signatures.hmacSha512(\"1\", \"23\") = " + Signatures.hmacSha512("1", "23"));
    }

}
