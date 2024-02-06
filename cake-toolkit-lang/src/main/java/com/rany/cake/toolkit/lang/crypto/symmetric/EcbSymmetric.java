package com.rany.cake.toolkit.lang.crypto.symmetric;


import com.rany.cake.toolkit.lang.codec.Base64s;
import com.rany.cake.toolkit.lang.crypto.enums.CipherAlgorithm;
import com.rany.cake.toolkit.lang.crypto.enums.PaddingMode;
import com.rany.cake.toolkit.lang.crypto.enums.WorkingMode;
import com.rany.cake.toolkit.lang.utils.Exceptions;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;


/**
 * ECB 模式非对称加密 AES DES 3DES
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/11/3 14:07
 */
public class EcbSymmetric extends BaseSymmetric {

    public EcbSymmetric(CipherAlgorithm algorithm, SecretKey secretKey) {
        this(algorithm, PaddingMode.PKCS5_PADDING, secretKey);
    }

    public EcbSymmetric(CipherAlgorithm algorithm, PaddingMode paddingMode, SecretKey secretKey) {
        super(algorithm, WorkingMode.ECB, paddingMode, secretKey);
    }

    @Override
    public byte[] encrypt(byte[] plain) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64s.encode(cipher.doFinal(this.zeroPadding(plain, cipher.getBlockSize())));
        } catch (Exception e) {
            throw Exceptions.encrypt("encrypt data error", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] text) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return this.clearZeroPadding(cipher.doFinal(Base64s.decode(text)));
        } catch (Exception e) {
            throw Exceptions.encrypt("decrypt data error", e);
        }
    }

}

