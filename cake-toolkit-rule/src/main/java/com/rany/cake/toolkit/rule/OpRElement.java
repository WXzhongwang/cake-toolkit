package com.rany.cake.toolkit.rule;


/**
 * 操作符
 *
 * @Author jiaoyang
 * @Date 2021/9/6
 * @Version 1.0
 */
public class OpRElement implements RElement {

    private OpType opType;

    public OpRElement(String value) {
        opType = OpType.parse(value);
    }


    @Override
    public String toMvelScript() {
        return " " + opType.getEx() + " ";
    }

    @Override
    public void validate() {
        if (opType == null) {
            throw new RuntimeException("存在不合法的操作符");
        }
    }

    public OpType getOpType() {
        return opType;
    }

    public void setOpType(OpType opType) {
        this.opType = opType;
    }
}
