package com.rany.cake.toolkit.lang.exception.argument;

/**
 * 验证对象为空异常
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email zhongshengwang.zsw@alibaba-inc.com
 */
public class NullArgumentException extends InvalidArgumentException {

    public NullArgumentException() {
    }

    public NullArgumentException(String message) {
        super(message);
    }

    public NullArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public NullArgumentException(Throwable cause) {
        super(cause);
    }

}
