package com.rany.cake.toolkit.lang.exception.argument;

/**
 * 带 code 的 exception
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class CodeArgumentException extends InvalidArgumentException {

    private int code;

    public CodeArgumentException() {
    }

    public CodeArgumentException(String message) {
        super(message);
    }

    public CodeArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public CodeArgumentException(Throwable cause) {
        super(cause);
    }

    public CodeArgumentException(int code) {
        this.code = code;
    }

    public CodeArgumentException(int code, String message) {
        super(message);
        this.code = code;
    }

    public CodeArgumentException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public CodeArgumentException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
