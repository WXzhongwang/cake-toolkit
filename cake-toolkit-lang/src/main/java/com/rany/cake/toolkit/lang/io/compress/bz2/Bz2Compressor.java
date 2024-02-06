package com.rany.cake.toolkit.lang.io.compress.bz2;


import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.io.compress.BaseFileCompressor;
import com.rany.cake.toolkit.lang.utils.Strings;
import com.rany.cake.toolkit.lang.utils.Valid;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * bzip2 压缩器
 * bzip2 只能压缩单个文件
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 16:37
 */
public class Bz2Compressor extends BaseFileCompressor {

    private BZip2CompressorOutputStream outputStream;

    /**
     * 压缩文件
     */
    private File compressFile;

    /**
     * 压缩输入流
     */
    private InputStream compressInputStream;

    /**
     * 压缩输出流
     *
     * @see #setCompressOutputStream(OutputStream)
     */
    private OutputStream compressOutputStream;

    public Bz2Compressor() {
        this(Const.SUFFIX_BZ2);
    }

    public Bz2Compressor(String suffix) {
        super(suffix);
    }

    @Override
    public void addFile(String file) {
        this.unsupportedOperation();
    }

    @Override
    public void addFile(File file) {
        this.unsupportedOperation();
    }

    @Override
    public void addFile(String name, String file) {
        this.unsupportedOperation();
    }

    @Override
    public void addFile(String name, File file) {
        this.unsupportedOperation();
    }

    @Override
    public void addFilePrefix(String prefix, String file) {
        this.unsupportedOperation();
    }

    @Override
    public void addFilePrefix(String prefix, File file) {
        this.unsupportedOperation();
    }

    @Override
    public void addFile(String name, byte[] bs) {
        this.unsupportedOperation();
    }

    @Override
    public void addFile(String name, InputStream in) {
        this.unsupportedOperation();
    }

    public void setCompressFile(String file) {
        Valid.notBlank(file, "file is null");
        this.setCompressFile(new File(file));
    }

    /**
     * 设置压缩文件
     *
     * @param file 压缩文件
     */
    public void setCompressFile(File file) {
        Valid.notNull(file, "file is null");
        Valid.isTrue(Files1.isFile(file), "compress file must be a normal file");
        this.compressFile = file;
        this.setFileName(file.getName());
    }

    public void setCompressFile(byte[] bs) {
        this.compressInputStream = Streams.toInputStream(bs);
    }

    /**
     * 设置压缩文件
     *
     * @param in in
     */
    public void setCompressFile(InputStream in) {
        this.compressInputStream = in;
    }

    /**
     * 设置压缩输出流
     *
     * @param compressOutputStream compressOutputStream
     */
    public void setCompressOutputStream(OutputStream compressOutputStream) {
        this.compressOutputStream = compressOutputStream;
    }

    @Override
    public void compress() throws Exception {
        Valid.isFalse(compressFile == null && compressInputStream == null, "bzip2 compress file just support compress one file");
        this.doCompress();
    }

    @Override
    public void doCompress() throws Exception {
        InputStream in = null;
        try {
            if (compressOutputStream != null) {
                this.outputStream = new BZip2CompressorOutputStream(compressOutputStream);
            } else {
                this.outputStream = new BZip2CompressorOutputStream(Files1.openOutputStreamFast(new File(this.getAbsoluteCompressPath())));
            }
            if (compressInputStream != null) {
                in = compressInputStream;
            } else {
                in = Files1.openInputStreamFast(compressFile);
            }
            Streams.transfer(in, outputStream);
            super.notify(Strings.EMPTY);
        } finally {
            Streams.close(outputStream);
            if (compressInputStream == null) {
                Streams.close(in);
            }
        }
    }

    @Override
    public BZip2CompressorOutputStream getCloseable() {
        return outputStream;
    }

}
