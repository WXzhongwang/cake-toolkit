package com.rany.cake.toolkit.nebula.exception;


import com.rany.cake.toolkit.nebula.enums.ErrorEnum;

/**
 * @author lxy
 */
public class NebulaExecException extends NebulaException {

    public NebulaExecException(String msg) {
        super(ErrorEnum.OPERATION_FAILED.getResponseCode(), msg);
    }
}
