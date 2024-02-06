package com.rany.cake.toolkit.lang.exception.argument;

/**
 * 对象验证不匹配异常
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class UnMatchArgumentException extends InvalidArgumentException {

    public UnMatchArgumentException() {
    }

    public UnMatchArgumentException(String message) {
        super(message);
    }

    public UnMatchArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnMatchArgumentException(Throwable cause) {
        super(cause);
    }

}
