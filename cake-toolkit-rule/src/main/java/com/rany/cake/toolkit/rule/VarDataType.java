package com.rany.cake.toolkit.rule;


/**
 * @Author jiaoyang
 * @Version 1.0
 * 变量支持的数据类型，含返回值和入参
 */
public enum VarDataType {

    STRING,

    LIST,

    BOOLEAN,

    DATE,

    ;


    public boolean typeMatch(Object param) {
        return true;
    }

}
