package com.rany.cake.toolkit.rule;


/**
 * @Author jiaoyang
 * @Version 1.0
 */
public class ConstString extends Const<String> {

    private final String value;

    public ConstString(String value) {
        this.value = value;
    }

    @Override
    public VarDataType getReturnType() {
        return VarDataType.STRING;
    }


    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toMvelScript() {
        return "'" + value + "'";
    }


    @Override
    public void validate() {
        if (value == null) {
            throw new RuntimeException("有空值");
        }
    }
}
