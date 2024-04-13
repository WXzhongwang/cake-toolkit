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

    private String code;

    public CodeArgumentException() {
    }


    public CodeArgumentException(Throwable cause) {
        super(cause);
    }

    public CodeArgumentException(String code) {
        this.code = code;
    }

    public CodeArgumentException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CodeArgumentException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }


    public CodeArgumentException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
