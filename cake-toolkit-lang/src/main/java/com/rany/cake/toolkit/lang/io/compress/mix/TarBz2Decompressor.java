package com.rany.cake.toolkit.lang.io.compress.mix;

import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.io.compress.BaseFileDecompressor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.OutputStream;

/**
 * tar.bz2 解压器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 21:08
 */
public class TarBz2Decompressor extends BaseFileDecompressor {

    private TarArchiveInputStream inputStream;

    public TarBz2Decompressor() {
        this(Const.SUFFIX_TAR_BZ2);
    }

    public TarBz2Decompressor(String suffix) {
        super(suffix);
    }

    @Override
    public void doDecompress() throws Exception {
        try (BufferedInputStream bi = new BufferedInputStream(Files1.openInputStreamFast(decompressFile));
             BZip2CompressorInputStream bz2In = new BZip2CompressorInputStream(bi)) {
            this.inputStream = new TarArchiveInputStream(bz2In);
            ArchiveEntry entry;
            while ((entry = inputStream.getNextEntry()) != null) {
                File file = new File(decompressTargetPath, entry.getName());
                if (entry.isDirectory()) {
                    Files1.mkdirs(file);
                } else {
                    try (OutputStream out = Files1.openOutputStreamFast(file)) {
                        Streams.transfer(inputStream, out);
                    }
                }
            }
        } finally {
            Streams.close(inputStream);
        }
    }

    @Override
    public TarArchiveInputStream getCloseable() {
        return inputStream;
    }
}
