package com.rany.cake.toolkit.nebula.exception;


import com.rany.cake.toolkit.nebula.common.ResponseService;

/**
 * @author lxy
 * @since 2024.12
 */
public class NebulaException extends RuntimeException {
    private String code;

    public NebulaException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public NebulaException(Throwable throwable) {
        super(throwable);
        this.code = throwable.getMessage();
    }

    public NebulaException(ResponseService responseService) {
        super(responseService.getResponseMessage());
        this.code = responseService.getResponseCode();
    }
}
