package com.rany.cake.toolkit.rule;


import java.util.ArrayList;
import java.util.List;

/**
 * @Author jiaoyang
 * @Version 1.0
 */
public class Expression implements RElement {

    private List<RElement> RElements = new ArrayList<>();

    public void addElement(RElement RElement) {
        RElements.add(RElement);
    }

    @Override
    public String toMvelScript() {
        if (RElements != null && !RElements.isEmpty()) {
            StringBuffer sb = new StringBuffer("(");

            if (RElements.size() == 3
                    && RElements.get(1) instanceof OpRElement
                    && ((OpRElement) RElements.get(1)).getOpType().getIsCustom()) {

                sb.append(String.format(
                        ((OpRElement) RElements.get(1)).getOpType().getCustomMethod(),
                        RElements.get(0).toMvelScript(),
                        RElements.get(2).toMvelScript())
                );
            } else {
                for (int i = 0; i < RElements.size(); i++) {
                    sb.append(RElements.get(i).toMvelScript());
                }
            }

            sb.append(")");

            return sb.toString();
        }
        return "";
    }

    @Override
    public void validate() {
        if (RElements != null) {
            for (int i = 0; i < RElements.size(); i++) {
                if (i % 2 == 1) {
                    if (!(RElements.get(i) instanceof OpRElement)) {
                        throw new RuntimeException(RElements.get(i).toMvelScript() + "附近缺少操作符");
                    }
                } else {
                    if (!(RElements.get(i) instanceof Const || RElements.get(i) instanceof Variable || RElements.get(i) instanceof Expression)) {
                        throw new RuntimeException(RElements.get(i).toMvelScript() + "附近缺少变量或者表达式或者常量");
                    }
                }

                RElements.get(i).validate();
            }
        }
    }

    public List<RElement> getRElements() {
        return RElements;
    }
}
