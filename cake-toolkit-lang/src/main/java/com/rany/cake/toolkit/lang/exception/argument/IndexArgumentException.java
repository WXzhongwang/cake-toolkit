package com.rany.cake.toolkit.lang.exception.argument;

/**
 * 对象验证索引异常
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class IndexArgumentException extends InvalidArgumentException {

    private int index;

    private int size;

    public IndexArgumentException() {
    }

    public IndexArgumentException(String message) {
        super(message);
    }

    public IndexArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexArgumentException(Throwable cause) {
        super(cause);
    }

    public IndexArgumentException(int index) {
        this.index = index;
    }

    public IndexArgumentException(int index, String message) {
        super(message);
        this.index = index;
    }

    public IndexArgumentException(int index, String message, Throwable cause) {
        super(message, cause);
        this.index = index;
    }

    public IndexArgumentException(int index, Throwable cause) {
        super(cause);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
