package com.rany.cake.toolkit.rule;


import java.util.List;

/**
 * @Author jiaoyang
 * @Version 1.0
 */
public class Option {

    private String value;

    private String label;

    private List<Option> children;


    public Option() {
    }

    ;

    public Option(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public Option(String value, String label, List<Option> children) {
        this.value = value;
        this.label = label;
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<Option> getChildren() {
        return children;
    }

    public void setChildren(List<Option> children) {
        this.children = children;
    }
}
