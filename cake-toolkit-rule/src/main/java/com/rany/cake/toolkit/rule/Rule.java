package com.rany.cake.toolkit.rule;


/**
 * @Author jiaoyang
 * @Version 1.0
 */
public class Rule {

    private Expression expression;

    private String stringExpression;


    public String toMvelScript() {
        if (stringExpression == null) {
            stringExpression = expression.toMvelScript();
        }
        return stringExpression;
    }


    /**
     * 表达式编译之前校验, 校验参数是否匹配
     */
    public void validate() {
        expression.validate();
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String getStringExpression() {
        return stringExpression;
    }

    public void setStringExpression(String stringExpression) {
        this.stringExpression = stringExpression;
    }
}
