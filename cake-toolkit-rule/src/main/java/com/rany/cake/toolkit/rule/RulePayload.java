package com.rany.cake.toolkit.rule;

/**
 * @Author jiaoyang
 * @Version 1.0
 */
public class RulePayload {

    private static final ThreadLocal<RulePayload> PAYLOAD_THREAD_LOCAL = new ThreadLocal<>();

    private Object payload;

    public static void setPayload(Object payload) {
        RulePayload rulePayload = new RulePayload();
        PAYLOAD_THREAD_LOCAL.remove();
        rulePayload.payload = payload;
        PAYLOAD_THREAD_LOCAL.set(rulePayload);
    }

    public static Object get() {
        if (PAYLOAD_THREAD_LOCAL.get() != null) {
            return PAYLOAD_THREAD_LOCAL.get().payload;
        }
        return null;
    }
}
