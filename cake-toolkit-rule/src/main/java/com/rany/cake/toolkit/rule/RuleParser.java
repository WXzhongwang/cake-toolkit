package com.rany.cake.toolkit.rule;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author zhongshengwang
 */
public class RuleParser {

    private final Logger logger = LoggerFactory.getLogger(RuleParser.class);

    public Rule parse(String ruleXml, Map<String, Object> varsMap) {
        try {
            Rule rule = new Rule();
            SAXReader reader = new SAXReader();
            reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            String encoding = "utf-8";
            // getEncoding(xml);
            InputSource source = new InputSource(new StringReader(ruleXml));
            source.setEncoding(encoding);
            Document doc = reader.read(source);


            Element root = doc.getRootElement();
            Expression expression = this.parseExpression(root, varsMap);
            rule.setExpression(expression);
            return rule;
        } catch (Exception e) {
            logger.error("规则解析失败:{}", ruleXml, e);
        }
        return null;
    }


    /**
     * 递归解析表达式
     *
     * @param root
     * @return
     */
    private Expression parseExpression(Element root, Map<String, Object> varsMap) {
        Expression expression = new Expression();
        Iterator it = root.elementIterator();
        while (it.hasNext()) {
            Element element = (Element) it.next();
            String name = element.getName();
            if ("element".equals(name)) {
                String type = element.attributeValue("type");
                if (type.equals("expression")) {
                    List<Element> childElements = element.elements();
                    if (childElements == null || childElements.size() == 0) {
                        throw new RuntimeException("表达式内容为空");
                    }
                    Expression childExpression = this.parseExpression(element, varsMap);
                    expression.addElement(childExpression);
                } else if (type.equals("variable")) {
                    Variable variable = (Variable) this.parseVariableAndConst(element, varsMap);
                    expression.addElement(variable);
                } else if (type.equals("const")) {
                    Const _const_ = (Const) this.parseVariableAndConst(element, varsMap);
                    expression.addElement(_const_);
                } else if (type.equals("op")) {
                    String op = element.attributeValue("value");
                    OpRElement opRElement = new OpRElement(op);
                    expression.addElement(opRElement);
                } else {
                    throw new RuntimeException("非法的元素类型：" + type);
                }
            }
        }
        return expression;
    }


    /**
     * 解析变量参数
     *
     * @param element
     * @return
     */
    private List<RElement> parseParams(Element element, Map<String, Object> varsMap) {
        List<RElement> _RElements = new ArrayList<>();
        Iterator it = element.elementIterator();
        while (it.hasNext()) {
            Element paramWrap = (Element) it.next();
            Element param = paramWrap.element("element");
            RElement _RElement = this.parseVariableAndConst(param, varsMap);
            _RElements.add(_RElement);
        }
        return _RElements;

    }

    /**
     * 解析变量和常量
     *
     * @param element
     * @return
     */
    private RElement parseVariableAndConst(Element element, Map<String, Object> varsMap) {
        String type = element.attributeValue("type");
        if (type.equals("variable")) {
            String variablCode = element.attributeValue("value");
            Variable _variable_ = (Variable) varsMap.get(variablCode);
            Variable _variable_new_ = _variable_.newInstance();
            _variable_new_.setSecondCode(element.attributeValue("secondCode"));

            if (element.elements() != null) {
                List<RElement> _params = this.parseParams(element, varsMap);
                _variable_new_.setParams(_params);
            }
            return _variable_new_;
        } else if (type.equals("const")) {
            String category = element.attributeValue("category").toLowerCase();
            String value = element.attributeValue("value");
            Const _const_ = null;
            if ("string".equals(category)
                    || "boolean".equals(category)
                    || "date".equals(category)
                    || "number".equals(category)) {
                _const_ = new ConstString(value);
            } else if ("list".equals(category)) {
                _const_ = new ConstList(value);
            } else {
                throw new IllegalArgumentException("category is invalid");
            }
            return _const_;
        }
        return null;
    }

}
