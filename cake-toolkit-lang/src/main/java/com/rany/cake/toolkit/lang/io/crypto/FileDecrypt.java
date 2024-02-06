package com.rany.cake.toolkit.lang.io.crypto;

import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.crypto.AES;
import com.rany.cake.toolkit.lang.crypto.Keys;
import com.rany.cake.toolkit.lang.crypto.enums.CipherAlgorithm;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Strings;

import javax.crypto.SecretKey;
import java.io.*;
import java.util.concurrent.Callable;

/**
 * 文件解密器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/7/15 18:03
 */
public class FileDecrypt implements Callable<Boolean> {

    private final BufferedReader reader;

    private final OutputStream out;

    private final String password;

    private final boolean autoClose;

    public FileDecrypt(File file, File dest, String password) {
        this(file, dest, password, Const.BUFFER_KB_8);
    }

    public FileDecrypt(String file, String dest, String password) {
        this(file, dest, password, Const.BUFFER_KB_8);
    }

    public FileDecrypt(InputStream in, OutputStream out, String password) {
        this(in, out, password, Const.BUFFER_KB_8);
    }

    public FileDecrypt(Reader reader, OutputStream out, String password) {
        this(reader, out, password, Const.BUFFER_KB_8);
    }

    public FileDecrypt(String file, String dest, String password, int bufferSize) {
        this(new File(file), new File(dest), password, bufferSize);
    }

    public FileDecrypt(File file, File dest, String password, int bufferSize) {
        this.autoClose = true;
        this.password = password;
        try {
            Files1.touch(dest);
            this.reader = new BufferedReader(new FileReader(file), bufferSize);
            this.out = Files1.openOutputStream(dest);
        } catch (Exception e) {
            throw Exceptions.ioRuntime(e);
        }
    }

    public FileDecrypt(InputStream in, OutputStream out, String password, int bufferSize) {
        this.autoClose = false;
        this.password = password;
        this.reader = new BufferedReader(new InputStreamReader(in), bufferSize);
        this.out = out;
    }

    public FileDecrypt(Reader reader, OutputStream out, String password, int bufferSize) {
        this.autoClose = false;
        this.password = password;
        this.reader = new BufferedReader(reader, bufferSize);
        this.out = out;
    }

    @Override
    public Boolean call() {
        SecretKey secretKey = Keys.generatorKey(password, CipherAlgorithm.AES);
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                byte[] d = AES.decrypt(Strings.bytes(line), secretKey);
                out.write(d);
            }
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (autoClose) {
                Streams.close(reader);
                Streams.close(out);
            }
        }
    }

}
