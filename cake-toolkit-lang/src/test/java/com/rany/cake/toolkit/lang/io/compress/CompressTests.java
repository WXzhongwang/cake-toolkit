package com.rany.cake.toolkit.lang.io.compress;

import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.compress.bz2.Bz2Compressor;
import com.rany.cake.toolkit.lang.io.compress.bz2.Bz2Decompressor;
import com.rany.cake.toolkit.lang.io.compress.gz.GzCompressor;
import com.rany.cake.toolkit.lang.io.compress.gz.GzDecompressor;
import com.rany.cake.toolkit.lang.io.compress.jar.JarCompressor;
import com.rany.cake.toolkit.lang.io.compress.jar.JarDecompressor;
import com.rany.cake.toolkit.lang.io.compress.mix.TarBz2Compressor;
import com.rany.cake.toolkit.lang.io.compress.mix.TarBz2Decompressor;
import com.rany.cake.toolkit.lang.io.compress.mix.TarGzCompressor;
import com.rany.cake.toolkit.lang.io.compress.mix.TarGzDecompressor;
import com.rany.cake.toolkit.lang.io.compress.tar.TarCompressor;
import com.rany.cake.toolkit.lang.io.compress.tar.TarDecompressor;
import com.rany.cake.toolkit.lang.io.compress.z7.Z7Compressor;
import com.rany.cake.toolkit.lang.io.compress.z7.Z7Decompressor;
import com.rany.cake.toolkit.lang.io.compress.zip.ZipCompressor;
import com.rany.cake.toolkit.lang.io.compress.zip.ZipDecompressor;
import org.junit.Test;

/**
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 19:19
 */
public class CompressTests {

    private String dir = "/Users/zhongshengwang/Desktop/zip/3.0";

    private String desktop = "/Users/zhongshengwang/Desktop/zip";

    private String target = "/Users/zhongshengwang/Desktop/zip/target";

    @Test
    public void zipCompress() throws Exception {
        ZipCompressor c = new ZipCompressor();
        // c.addFile(dir);
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void zipDecompress() throws Exception {
        ZipDecompressor d = new ZipDecompressor();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.zip");
        d.setDecompressTargetPath(target);
        d.decompress();
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void jarCompress() throws Exception {
        JarCompressor c = new JarCompressor();
        // c.addFile(dir);
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void jarDecompress() throws Exception {
        JarDecompressor d = new JarDecompressor();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.jar");
        d.setDecompressTargetPath(target);
        d.decompress();
//        Files1.delete(target);
//        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void z7Compress() throws Exception {
        Z7Compressor c = new Z7Compressor();
        // c.addFile(dir);
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void z7Decompress() throws Exception {
        Z7Decompressor d = new Z7Decompressor();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.7z");
        d.setDecompressTargetPath(target);
        d.decompress();
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void tarCompress() throws Exception {
        TarCompressor c = new TarCompressor();
        c.addFile("/Users/zhongshengwang/workspace/cake-toolkit");
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("test-tar-zip-dir");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void tarDecompress() throws Exception {
        TarDecompressor d = new TarDecompressor();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.tar");
        d.setDecompressTargetPath(target);
        d.decompress();
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void gzCompress() throws Exception {
        GzCompressor c = new GzCompressor();
        c.setCompressFile("REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void gzDecompress() throws Exception {
        GzDecompressor d = new GzDecompressor();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.gz");
        d.setDecompressTargetPath(target);
        d.decompress();
        System.out.println(d.getDecompressTargetFile());
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void bz2Compress() throws Exception {
        Bz2Compressor c = new Bz2Compressor();
        c.setCompressFile("readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void bz2Decompress() throws Exception {
        Bz2Decompressor d = new Bz2Decompressor();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.bz2");
        d.setDecompressTargetPath(target);
        d.setDecompressTargetFileName("README");
        d.decompress();
        System.out.println(d.getDecompressTargetFile());
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void tarGzCompress() throws Exception {
        TarGzCompressor c = new TarGzCompressor();
        // c.addFile(dir);
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void tarGzDecompress() throws Exception {
        TarGzDecompressor d = new TarGzDecompressor();
        d.setDecompressFile("C:\\Users\\ljh15\\Desktop\\3.0.tar.gz");
        d.setDecompressTargetPath(target);
        d.decompress();
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void tarBz2Compress() throws Exception {
        TarBz2Compressor c = new TarBz2Compressor();
        //c.addFile(dir);
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);
    }

    @Test
    public void tarBz2Decompress() throws Exception {
        TarBz2Decompressor d = new TarBz2Decompressor();
        d.setDecompressFile("C:\\Users\\ljh15\\Desktop\\3.0.tar.bz2");
        d.setDecompressTargetPath(target);
        d.decompress();
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

    @Test
    public void zip() {
        Compresses.zip(dir, desktop + "/3.0.zip");
    }

    @Test
    public void unzip() {
        Compresses.unzip(desktop + "/3.0.zip", target);
    }

    @Test
    public void compressAll() throws Exception {
        this.zipCompress();
        this.jarCompress();
        this.z7Compress();
        this.tarCompress();
        this.gzCompress();
        this.bz2Compress();
        this.tarGzCompress();
        this.tarBz2Compress();
    }

    @Test
    public void decompressAll() throws Exception {
        this.zipDecompress();
        this.jarDecompress();
        this.z7Decompress();
        this.tarDecompress();
        this.gzDecompress();
        this.bz2Decompress();
        this.tarGzDecompress();
        this.tarBz2Decompress();
    }

    @Test
    public void testEnum() throws Exception {
        CompressTypeEnum zip = CompressTypeEnum.ZIP;
        FileCompressor c = zip.compressor().get();
        //c.addFile(dir);
        c.addFile("REAMDE", "readme".getBytes());
        c.addFile("_/REAMDE", "readme".getBytes());
        c.setCompressPath(desktop);
        c.setFileName("3.0");
        c.compress();
        String absoluteCompressPath = c.getAbsoluteCompressPath();
        System.out.println(absoluteCompressPath);

        FileDecompressor d = zip.decompressor().get();
        d.setDecompressFile("/Users/zhongshengwang/Desktop/zip/3.0.zip");
        d.setDecompressTargetPath(target);
        d.decompress();
        Files1.delete(target);
        Files1.delete(d.getDecompressFile());
    }

}
