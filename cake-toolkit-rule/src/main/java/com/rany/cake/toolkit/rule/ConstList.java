package com.rany.cake.toolkit.rule;


import java.util.Arrays;
import java.util.List;

/**
 * @Author jiaoyang
 * @Date 2021/9/6
 * @Version 1.0
 */
public class ConstList extends Const<List<String>> {

    private final List<String> value;


    public ConstList(String value) {
        this.value = Arrays.asList(value.split(","));
    }

    @Override
    public String toMvelScript() {
        StringBuffer sb = new StringBuffer("[");
        if (value != null) {
            for (int i = 0; i < value.size(); i++) {
                sb.append("'").append(value.get(i)).append("'").append(",");
            }
        }
        sb.append("]");
        return value.toString();

    }

    @Override
    public void validate() {
        if (value == null || value.isEmpty()) {
            throw new RuntimeException("有空值");
        }
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public VarDataType getReturnType() {
        return VarDataType.LIST;
    }
}
