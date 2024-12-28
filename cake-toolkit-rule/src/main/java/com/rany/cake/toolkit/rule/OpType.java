package com.rany.cake.toolkit.rule;

/**
 * @Author jiaoyang
 * @Version 1.0
 */
public enum OpType {

    EQUAL("==", "等于", false, ""),

    NOT_EQUAL("!=", "不等于", false, ""),

    GREAT_THAN(">", "大于", false, ""),

    GREAT_THAN_EQUAL_TO(">=", "大于或等于", false, ""),

    LESS_THAN("<", "小于", false, ""),

    LESS_THAN_EQUAL_TO("<=", "小于或等于", false, ""),

    CONTAIN("contains", "包含", false, ""),

    NOT_CONTAIN("not contains", "不包含", true, "extOpExec.notContains(%s,%s)"),

    AND("&&", "且", false, ""),

    OR("||", "或", false, ""),
    ;

    private String ex;

    private String name;

    //自定义标识
    private Boolean isCustom;

    //自定义方法
    private String customMethod;

    OpType(String ex, String name, Boolean isCustom, String customMethod) {
        this.ex = ex;
        this.name = name;
        this.isCustom = isCustom;
        this.customMethod = customMethod;
    }

    public String getEx() {
        return ex;
    }

    public String getName() {
        return name;
    }

    public Boolean getIsCustom() {
        return isCustom;
    }

    public String getCustomMethod() {
        return customMethod;
    }

    public static OpType parse(String value) {
        OpType[] opTypes = OpType.values();
        for (OpType opType : opTypes) {
            if (opType.getName().equals(value)) {
                return opType;
            }
        }
        return null;
    }
}
