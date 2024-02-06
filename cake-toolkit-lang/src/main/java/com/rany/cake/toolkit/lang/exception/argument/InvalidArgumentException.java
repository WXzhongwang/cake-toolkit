package com.rany.cake.toolkit.lang.exception.argument;

/**
 * 对象验证不合法异常
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email zhongshengwang.zsw@alibaba-inc.com
 */
public class InvalidArgumentException extends RuntimeException {

    public InvalidArgumentException() {
    }

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidArgumentException(Throwable cause) {
        super(cause);
    }

}
