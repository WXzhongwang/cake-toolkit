package com.rany.cake.toolkit.rule;


/**
 * @Author jiaoyang
 * @Version 1.0
 */
public interface RElement {

    /**
     * 转换为mvel脚本
     *
     * @return mvel脚本
     */
    String toMvelScript();

    /**
     * 表达式静态检查
     */
    void validate() throws RuntimeException;

}
