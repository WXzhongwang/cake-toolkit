package com.rany.cake.toolkit.cms.exception;


public class CmsException extends RuntimeException{

    public CmsException() {
    }

    public CmsException(String message) {
        super(message);
    }

    public CmsException(String message, Throwable cause) {
        super(message, cause);
    }
}
