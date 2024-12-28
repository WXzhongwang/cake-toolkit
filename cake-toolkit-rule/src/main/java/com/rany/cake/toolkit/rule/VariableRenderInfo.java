package com.rany.cake.toolkit.rule;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class VariableRenderInfo {


    private List<OpType> opTypes;

    private String rightType;

    private List<VarParam> varParams;

    private JSONObject extInfo;

    public List<OpType> getOpTypes() {
        return opTypes;
    }

    public void setOpTypes(List<OpType> opTypes) {
        this.opTypes = opTypes;
    }

    public String getRightType() {
        return rightType;
    }

    public void setRightType(String rightType) {
        this.rightType = rightType;
    }

    public List<VarParam> getVarParams() {
        return varParams;
    }

    public void setVarParams(List<VarParam> varParams) {
        this.varParams = varParams;
    }

    public JSONObject getExtInfo() {
        return extInfo;
    }

    public void setExtInfo(JSONObject extInfo) {
        this.extInfo = extInfo;
    }
}
