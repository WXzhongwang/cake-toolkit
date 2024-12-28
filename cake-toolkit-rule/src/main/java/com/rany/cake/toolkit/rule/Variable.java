package com.rany.cake.toolkit.rule;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author jiaoyang
 * @Version 1.0
 */
public abstract class Variable implements RElement {

    private final Logger logger = LoggerFactory.getLogger(Variable.class);

    private String variableCode;

    private String secondCode;

    private List<RElement> params;


    /**
     * 变量名称
     *
     * @return 变量名称
     */
    public abstract String getCode();

    /**
     * 变量返回值类型
     *
     * @return 变量返回值类型
     */
    public abstract VarDataType getReturnType(String secondCode);

    /**
     * 右值类型
     *
     * @return 右值类型
     */
    public abstract RightType getRightType(String secondCode);


    /**
     * 右表达式变量值候选项
     *
     * @return 候选项
     */
    public abstract List<Option> getRightOptions(String secondCode, Map<String, String> queryParam);


    /**
     * 支持的操作符
     *
     * @return 支持的操作符类型
     */
    public abstract List<OpType> getSupportOpTypes(String secondCode);


    /**
     * 变量支持的参数类型
     *
     * @return 变量支持的参数类型
     */
    public abstract List<VarParam> getParamsDesc(String secondCode);

    /**
     * 获取参数的候选项
     *
     * @param extQueryParam 扩展查询参数
     * @param paramName     参数名称
     * @return 候选项
     */
    public abstract List<Option> getParamsOptions(String secondCode, String paramName, Map<String, String> extQueryParam);

    /**
     * 新建一个当前变量的实例
     *
     * @return 当前变量的实例
     */
    public Variable newInstance() {
        String className = getClass().getName();
        try {
            Variable variable = (Variable) Class.forName(className).newInstance();
            variable.variableCode = this.variableCode;
            return variable;
        } catch (Exception e) {
            logger.error("变量实例化失败:{}", className, e);
            return null;
        }
    }

    public String getVariableCode() {
        if (variableCode == null || variableCode.isEmpty()) {
            String className = getClass().getName();
            variableCode = className.substring(className.lastIndexOf('.') + 1);

            variableCode = Character.toLowerCase(variableCode.charAt(0)) +
                    variableCode.substring(1);
        }
        return variableCode;
    }

    @Override
    public void validate() {
        List<VarParam> varParams = this.getParamsDesc(secondCode);
        if (varParams != null && !varParams.isEmpty()) {
            if (params != null) {
                if (varParams.size() != params.size()) {
                    throw new RuntimeException(this.getCode() + "缺少参数");
                }
                for (int i = 0; i < params.size(); i++) {
                    RElement RElement = params.get(i);
                    RElement.validate();
                }
            } else {
                throw new RuntimeException(this.getCode() + "缺少参数");
            }
        } else {
            if (params != null && !params.isEmpty()) {
                throw new RuntimeException(this.getCode() + "没有参数，但是填写了参数");
            }
        }
    }

    /**
     * 变量运行时执行入口
     *
     * @param params
     * @return
     */
    public Object execute(String secondCode, List<Object> params) {
        if (!checkParamType(secondCode, params)) {
            throw new RuntimeException("");
        }

        Object result = executeInner(secondCode, params);

        if (!checkResultType(secondCode, result)) {
            throw new RuntimeException("");
        }

        return result;
    }

    /**
     * 校验参数类型
     *
     * @param params
     * @return
     */
    private boolean checkParamType(String secondCode, List<Object> params) {

        List<VarParam> varParams = this.getParamsDesc(secondCode);
        //先校验长度
        if (params != null && varParams != null) {
            if (params.size() != varParams.size()) {
                return false;
            }
        }
        //校验类型
        if (params != null) {
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                VarParam varParam = varParams.get(i);
                if (varParam != null && !varParam.getVarDataType().typeMatch(param)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean checkResultType(String varDisplayName, Object result) {
        VarDataType resultType = this.getReturnType(varDisplayName);
        if (result != null) {
            return resultType.typeMatch(result);
        }
        return true;
    }


    public final void setParams(List<RElement> params) {
        this.params = params;
    }

    public final void setSecondCode(String displayName) {
        this.secondCode = displayName;
    }


    /**
     * 变量执行方法
     *
     * @param secondCode 场景code
     * @param params     参数
     * @return 返回值
     */
    public abstract Object executeInner(String secondCode, List<Object> params);


    @Override
    public String toMvelScript() {
        String objName = this.getVariableCode();

        objName = Character.toLowerCase(objName.charAt(0)) +
                objName.substring(1);

        List<RElement> elements = this.params;
        String s = "\'" + secondCode + "\'" + ",";
        if (elements != null && !elements.isEmpty()) {
            s += "[";
            for (RElement element : elements) {
                s += element.toMvelScript() + ",";
            }
            s += "]";
        } else {
            s += "[]";
        }

        return " " + objName + ".execute(" + s + ") ";
    }

    public boolean display() {
        return true;
    }
}
