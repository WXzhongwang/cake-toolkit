package com.rany.cake.toolkit.lang.io.compress;

import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.utils.Valid;

import java.io.File;

/**
 * 文件解压器 基类
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/9/27 21:02
 */
public abstract class BaseFileDecompressor implements FileDecompressor {

    /**
     * 解压文件
     */
    protected File decompressFile;

    /**
     * 解压路径
     */
    protected File decompressTargetPath;

    /**
     * 后缀
     */
    protected String suffix;

    public BaseFileDecompressor(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public void setDecompressFile(String decompressFile) {
        this.setDecompressFile(new File(decompressFile));
    }

    @Override
    public void setDecompressFile(File decompressFile) {
        this.decompressFile = decompressFile;
    }

    @Override
    public void setDecompressTargetPath(String decompressTargetPath) {
        this.setDecompressTargetPath(new File(decompressTargetPath));
    }

    @Override
    public void setDecompressTargetPath(File decompressPath) {
        this.decompressTargetPath = decompressPath;
    }

    @Override
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    @Override
    public void decompress() throws Exception {
        Valid.notNull(decompressFile, "decompress file is null");
        Valid.notNull(decompressTargetPath, "decompress target path is null");
        Valid.isTrue(Files1.isFile(decompressFile), "decompress file is not a file");
        this.doDecompress();
    }

    /**
     * 执行解压
     *
     * @throws Exception Exception
     */
    protected abstract void doDecompress() throws Exception;

    @Override
    public File getDecompressFile() {
        return decompressFile;
    }

    @Override
    public File getDecompressTargetPath() {
        return decompressTargetPath;
    }

    @Override
    public String getSuffix() {
        return suffix;
    }

}
