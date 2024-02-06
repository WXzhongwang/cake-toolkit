package com.rany.cake.toolkit.lang.io.compress.mix;

import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.io.compress.BaseFileCompressor;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * tar.gz 压缩器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 16:37
 */
public class TarGzCompressor extends BaseFileCompressor {

    private TarArchiveOutputStream outputStream;

    public TarGzCompressor() {
        this(Const.SUFFIX_TAR_GZ);
    }

    public TarGzCompressor(String suffix) {
        super(suffix);
    }

    @Override
    public void doCompress() throws Exception {
        try (BufferedOutputStream fileOut = new BufferedOutputStream(Files1.openOutputStreamFast(this.getAbsoluteCompressPath()));
             GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(fileOut)) {
            this.outputStream = new TarArchiveOutputStream(gzOut);
            // 设置压缩文件
            for (Map.Entry<String, File> fileEntity : compressFiles.entrySet()) {
                ArchiveEntry entity = outputStream.createArchiveEntry(fileEntity.getValue(), fileEntity.getKey());
                outputStream.putArchiveEntry(entity);
                try (InputStream in = Files1.openInputStreamFast(fileEntity.getValue())) {
                    Streams.transfer(in, outputStream);
                }
                outputStream.closeArchiveEntry();
                super.notify(fileEntity.getKey());
            }
            for (Map.Entry<String, InputStream> fileEntity : compressStreams.entrySet()) {
                TarArchiveEntry entity = new TarArchiveEntry(fileEntity.getKey());
                entity.setSize(fileEntity.getValue().available());
                outputStream.putArchiveEntry(entity);
                Streams.transfer(fileEntity.getValue(), outputStream);
                outputStream.closeArchiveEntry();
                super.notify(fileEntity.getKey());
            }
            outputStream.finish();
        } finally {
            Streams.close(outputStream);
        }
    }

    @Override
    public TarArchiveOutputStream getCloseable() {
        return outputStream;
    }

}
