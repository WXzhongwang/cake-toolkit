package com.rany.cake.toolkit.lang.exception;

/**
 * cron 转化运行时异常
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/8/26 17:50
 */
public class ParseCronException extends ParseRuntimeException {

    public ParseCronException() {
    }

    public ParseCronException(int errorOffset) {
        super(errorOffset);
    }

    public ParseCronException(String message) {
        super(message);
    }

    public ParseCronException(String message, int errorOffset) {
        super(message, errorOffset);
    }

}
