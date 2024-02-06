package com.rany.cake.toolkit.lang.io.compress.jar;

import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.io.compress.BaseFileDecompressor;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * jar 解压器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/28 9:00
 */
public class JarDecompressor extends BaseFileDecompressor {

    private JarFile jarFile;

    public JarDecompressor() {
        this(Const.SUFFIX_JAR);
    }

    public JarDecompressor(String suffix) {
        super(suffix);
    }

    @Override
    public void doDecompress() throws Exception {
        try {
            this.jarFile = new JarFile(decompressFile);
            Enumeration<?> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                File file = new File(decompressTargetPath, entry.getName());
                if (entry.isDirectory()) {
                    Files1.mkdirs(file);
                } else {
                    try (InputStream in = jarFile.getInputStream(entry);
                         OutputStream out = Files1.openOutputStreamFast(file)) {
                        Streams.transfer(in, out);
                    }
                }
            }
        } finally {
            Streams.close(jarFile);
        }
    }

    @Override
    public JarFile getCloseable() {
        return jarFile;
    }

}
