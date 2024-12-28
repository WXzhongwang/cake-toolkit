package com.rany.cake.toolkit.rule;

import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhongshengwang
 */
public class RuleEngine {

    private final Map<String, Object> varsMap = new HashMap<>();

    private final Map<String, Object> variablesNameMap = new HashMap<>();

    private final RuleParser ruleParser = new RuleParser();

    private VariableResolverFactory variableResolverFactory;


    public void init(List<Variable> variableList) {
        varsMap.put("extOpExec", ExtOpExec.getInstance());
        for (Variable variable : variableList) {
            variablesNameMap.put(variable.getCode(), variable);
            varsMap.put(variable.getVariableCode(), variable);
        }

        variableResolverFactory = new MapVariableResolverFactory(varsMap);
    }

    public Rule parseRuleXml(String ruleXml) {
        if (variablesNameMap.isEmpty()) {
            throw new RuntimeException("Please init first");
        }
        return ruleParser.parse(ruleXml, variablesNameMap);
    }


    public boolean exec(Rule rule) {
        try {
            String script = rule.toMvelScript();
            return (Boolean) MVEL.eval(script, variableResolverFactory);
        } catch (RuleInterruptException e) {
            return false;
        }
    }

    public boolean exec(Rule rule, Object payload) {
        try {
            RulePayload.setPayload(payload);
            String script = rule.toMvelScript();
            return (Boolean) MVEL.eval(script, variableResolverFactory);
        } catch (Exception e) {
            Throwable cause = getExceptionCause(e);
            if (cause instanceof RuleInterruptException) {
                return false;
            } else {
                throw e;
            }
        }
    }

    private Throwable getExceptionCause(Throwable e) {
        Throwable cause = e.getCause();
        if (cause != null) {
            return getExceptionCause(cause);
        }
        return e;
    }


}
