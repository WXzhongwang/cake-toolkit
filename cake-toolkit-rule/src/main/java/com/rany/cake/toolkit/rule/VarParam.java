package com.rany.cake.toolkit.rule;


/**
 * @Author jiaoyang
 * @Version 1.0
 */
public class VarParam {

    private String name;

    private VarDataType varDataType;

    private Boolean hasOptions;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Boolean getHasOptions() {
        return hasOptions;
    }

    public void setHasOptions(Boolean hasOptions) {
        this.hasOptions = hasOptions;
    }

    public VarDataType getVarDataType() {
        return varDataType;
    }

    public void setVarDataType(VarDataType varDataType) {
        this.varDataType = varDataType;
    }
}
