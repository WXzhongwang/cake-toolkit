package com.rany.cake.toolkit.rule;

import com.alibaba.fastjson.JSONObject;

public class VariableDesc {


    private String displayName;


    private String code;

    private String secondCode;


    private JSONObject renderInfo;


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSecondCode() {
        return secondCode;
    }

    public void setSecondCode(String secondCode) {
        this.secondCode = secondCode;
    }

    public JSONObject getRenderInfo() {
        return renderInfo;
    }

    public void setRenderInfo(JSONObject renderInfo) {
        this.renderInfo = renderInfo;
    }
}
