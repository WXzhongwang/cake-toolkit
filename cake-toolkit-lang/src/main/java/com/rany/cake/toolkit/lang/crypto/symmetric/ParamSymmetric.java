package com.rany.cake.toolkit.lang.crypto.symmetric;


import com.rany.cake.toolkit.lang.codec.Base64s;
import com.rany.cake.toolkit.lang.crypto.enums.CipherAlgorithm;
import com.rany.cake.toolkit.lang.crypto.enums.PaddingMode;
import com.rany.cake.toolkit.lang.crypto.enums.WorkingMode;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.lang.utils.Valid;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import java.security.spec.AlgorithmParameterSpec;


/**
 * CBC CFB OFB FTP GCM 模式非对称加密 AES DES 3DES
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/11/3 14:45
 */
public class ParamSymmetric extends BaseSymmetric {

    /**
     * 参数规格
     */
    private final AlgorithmParameterSpec paramSpec;

    /**
     * aad
     */
    private byte[] aad;

    public ParamSymmetric(CipherAlgorithm cipherAlgorithm, WorkingMode workingMode, SecretKey secretKey, AlgorithmParameterSpec paramSpec) {
        this(cipherAlgorithm, workingMode, PaddingMode.PKCS5_PADDING, secretKey, paramSpec);
    }

    public ParamSymmetric(CipherAlgorithm cipherAlgorithm, WorkingMode workingMode, PaddingMode paddingMode, SecretKey secretKey, AlgorithmParameterSpec paramSpec) {
        super(cipherAlgorithm, workingMode, paddingMode, secretKey);
        this.paramSpec = Valid.notNull(paramSpec, "paramSpec is null");
    }

    public void setAad(String aad) {
        this.aad = Strings.bytes(aad);
    }

    public void setAad(byte[] aad) {
        this.aad = aad;
    }

    @Override
    public byte[] encrypt(byte[] plain) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
            if (aad != null) {
                cipher.updateAAD(aad);
            }
            return Base64s.encode(cipher.doFinal(this.zeroPadding(plain, cipher.getBlockSize())));
        } catch (Exception e) {
            throw Exceptions.encrypt("encrypt data error", e);
        }
    }

    @Override
    public byte[] decrypt(byte[] text) {
        try {
            Cipher cipher = super.getCipher();
            cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
            if (aad != null) {
                cipher.updateAAD(aad);
            }
            return this.clearZeroPadding(cipher.doFinal(Base64s.decode(text)));
        } catch (Exception e) {
            throw Exceptions.encrypt("decrypt data error", e);
        }
    }

}
