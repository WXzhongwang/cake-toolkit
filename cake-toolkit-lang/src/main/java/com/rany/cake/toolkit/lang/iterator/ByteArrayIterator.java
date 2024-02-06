package com.rany.cake.toolkit.lang.iterator;

import com.rany.cake.toolkit.lang.io.SafeCloseable;
import com.rany.cake.toolkit.lang.io.Streams;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Valid;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Iterator;

/**
 * 流迭代器
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/26 13:24
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ByteArrayIterator implements Iterator<Integer>, Iterable<Integer>, SafeCloseable, Serializable {

    private final InputStream in;

    private final byte[] buffer;

    private int read;

    private boolean isRead;

    private boolean autoClose;

    public ByteArrayIterator(InputStream in, byte[] buffer) {
        Valid.notNull(in, "input stream is null");
        Valid.notNull(buffer, "buffer is null");
        this.in = in;
        this.buffer = buffer;
    }

    /**
     * 设置流自动关闭
     *
     * @param autoClose 是否自动关闭
     * @return this
     */
    public ByteArrayIterator autoClose(boolean autoClose) {
        this.autoClose = autoClose;
        return this;
    }

    /**
     * 设置偏移量
     *
     * @param skip 偏移量
     * @return this
     */
    public ByteArrayIterator skip(long skip) {
        try {
            in.skip(skip);
            return this;
        } catch (IOException e) {
            throw Exceptions.ioRuntime(e);
        }
    }

    @Override
    public boolean hasNext() {
        if (read == -1) {
            return false;
        } else if (isRead) {
            return true;
        }
        try {
            this.read = in.read(buffer);
            this.isRead = true;
            return read != -1;
        } catch (IOException e) {
            throw Exceptions.ioRuntime(e);
        }
    }

    @Override
    public Integer next() {
        if (!this.hasNext()) {
            throw Exceptions.noSuchElement("no more data");
        }
        this.isRead = false;
        return read;
    }

    @Override
    public void close() {
        this.read = -1;
        this.isRead = false;
        if (autoClose) {
            Streams.close(in);
        }
    }

    @Override
    public Iterator<Integer> iterator() {
        return this;
    }

}
