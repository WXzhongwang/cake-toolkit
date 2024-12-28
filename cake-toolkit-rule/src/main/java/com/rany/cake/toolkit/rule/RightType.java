package com.rany.cake.toolkit.rule;

/**
 * @author zhongshengwang
 */

public enum RightType {

    TEXT("text"),

    DATE("date"),

    TIME("time"),

    SELECT("select"),

    DIGIT("digit"),


    CUSTOM("custom"),

    ;

    private String name;

    RightType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
