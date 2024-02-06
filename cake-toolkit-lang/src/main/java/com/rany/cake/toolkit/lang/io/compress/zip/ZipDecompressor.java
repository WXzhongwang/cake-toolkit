package com.rany.cake.toolkit.lang.io.compress.zip;

import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.io.compress.BaseFileDecompressor;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * zip 解压器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 21:08
 */
public class ZipDecompressor extends BaseFileDecompressor {

    private ZipFile zipFile;

    public ZipDecompressor() {
        this(Const.SUFFIX_ZIP);
    }

    public ZipDecompressor(String suffix) {
        super(suffix);
    }

    @Override
    public void doDecompress() throws Exception {
        try {
            this.zipFile = new ZipFile(decompressFile);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                File file = new File(decompressTargetPath, entry.getName());
                if (entry.isDirectory()) {
                    Files1.mkdirs(file);
                } else {
                    try (InputStream in = zipFile.getInputStream(entry);
                         OutputStream out = Files1.openOutputStreamFast(file)) {
                        Streams.transfer(in, out);
                    }
                }
            }
        } finally {
            Streams.close(zipFile);
        }
    }

    @Override
    public ZipFile getCloseable() {
        return zipFile;
    }

}
