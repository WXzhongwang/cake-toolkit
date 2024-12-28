package com.rany.cake.toolkit.rule;


import java.util.List;

/**
 * @author zhongshengwang
 */
public class ExtOpExec {
    private ExtOpExec() {
    }

    private static class SingletonHolder {
        private static final ExtOpExec INSTANCE = new ExtOpExec();
    }

    public static ExtOpExec getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 不包含处理逻辑
     *
     * @param variables
     * @param constValue
     * @return
     */
    public boolean notContains(List<String> variables, String constValue) {
        if (variables != null) {
            return !variables.contains(constValue);
        }
        return false;
    }

}