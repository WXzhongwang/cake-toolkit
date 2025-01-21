package com.rany.cake.toolkit.cms.util;


public enum EnvEnum {

    /**
     * 生产环境
     */
    prod(new String[]{"prod", "production"}),
    /**
     * 预发环境
     */
    pre(new String[]{"pre", "staging", "prepub"}),

    /**
     * 开发环境
     */
    testing(new String[]{"testing", "test"}),
    /**
     * 开发环境
     */
    dev(new String[]{"dev", "development"});

    public final String[] value;

    EnvEnum(String[] value) {
        this.value = value;
    }

}
