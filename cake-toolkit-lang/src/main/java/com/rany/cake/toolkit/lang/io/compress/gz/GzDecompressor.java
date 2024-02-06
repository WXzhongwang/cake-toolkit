package com.rany.cake.toolkit.lang.io.compress.gz;

import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.id.ObjectIds;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.io.compress.BaseFileDecompressor;
import com.rany.cake.toolkit.lang.utils.Objects1;
import com.rany.cake.toolkit.lang.utils.Valid;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipParameters;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

/**
 * gzip 解压器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 21:08
 */
public class GzDecompressor extends BaseFileDecompressor {

    private GzipCompressorInputStream inputStream;

    /**
     * 解压文件输入流
     */
    private InputStream decompressInputStream;

    /**
     * 解压产物输出流
     */
    private OutputStream decompressTargetOutputStream;

    /**
     * 解压产物名称
     */
    private String decompressTargetFileName;

    /**
     * 解压产物路径
     */
    private File decompressTargetFile;

    public GzDecompressor() {
        this(Const.SUFFIX_GZ);
    }

    public GzDecompressor(String suffix) {
        super(suffix);
    }

    /**
     * 设置解压文件输入流
     *
     * @param decompressInputStream 解压文件输入流
     */
    public void setDecompressInputStream(InputStream decompressInputStream) {
        this.decompressInputStream = decompressInputStream;
    }

    /**
     * 设置解压产物输出流
     *
     * @param decompressTargetOutputStream 解压文件输出流
     */
    public void setDecompressTargetOutputStream(OutputStream decompressTargetOutputStream) {
        this.decompressTargetOutputStream = decompressTargetOutputStream;
    }

    /**
     * 设置解压产物名称
     *
     * @param decompressTargetFileName decompressTargetFileName
     */
    public void setDecompressTargetFileName(String decompressTargetFileName) {
        this.decompressTargetFileName = decompressTargetFileName;
    }

    @Override
    public void decompress() throws Exception {
        if (decompressFile != null) {
            Valid.isTrue(Files1.isFile(decompressFile), "decompress file is not a file");
        } else {
            Valid.notNull(decompressInputStream, "decompress file and decompress input stream is null");
        }
        if (decompressTargetPath == null) {
            Valid.notNull(decompressTargetOutputStream, "decompress target path and is decompress target output stream null");
        }
        this.doDecompress();
    }

    @Override
    public void doDecompress() throws Exception {
        OutputStream out = null;
        try {
            if (decompressInputStream != null) {
                this.inputStream = new GzipCompressorInputStream(decompressInputStream);
            } else {
                this.inputStream = new GzipCompressorInputStream(Files1.openInputStreamFast(decompressFile));
            }
            String entityName = Optional.ofNullable(inputStream.getMetaData())
                    .map(GzipParameters::getFilename)
                    .orElse(null);
            if (decompressTargetFileName == null) {
                // 配置 > entity > file > objectId
                this.decompressTargetFileName = Objects1.def(entityName, Optional.ofNullable(decompressFile)
                        .map(File::getName)
                        .map(s -> s.substring(0, s.length() - suffix.length() - 1))
                        .orElseGet(ObjectIds::nextId));
            }
            if (decompressTargetOutputStream != null) {
                out = decompressTargetOutputStream;
            } else {
                this.decompressTargetFile = new File(decompressTargetPath, decompressTargetFileName);
                out = Files1.openOutputStream(decompressTargetFile);
            }
            Streams.transfer(inputStream, out);
        } finally {
            Streams.close(inputStream);
            if (decompressTargetOutputStream == null) {
                Streams.close(out);
            }
        }
    }

    public File getDecompressTargetFile() {
        return decompressTargetFile;
    }

    public String getDecompressTargetFileName() {
        return decompressTargetFileName;
    }

    @Override
    public GzipCompressorInputStream getCloseable() {
        return inputStream;
    }

}
