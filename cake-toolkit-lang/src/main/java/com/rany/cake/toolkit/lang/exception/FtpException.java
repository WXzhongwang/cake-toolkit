package com.rany.cake.toolkit.lang.exception;

/**
 * FTP 异常
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/3/17 18:46
 */
public class FtpException extends RuntimeException {

    public FtpException() {
    }

    public FtpException(String message) {
        super(message);
    }

    public FtpException(String message, Throwable cause) {
        super(message, cause);
    }

    public FtpException(Throwable cause) {
        super(cause);
    }

}
