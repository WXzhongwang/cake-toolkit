package com.rany.cake.toolkit.lang.wrapper;

import com.alibaba.fastjson.annotation.JSONField;
import com.rany.cake.toolkit.lang.CloneSupport;
import com.rany.cake.toolkit.lang.KitLangConfiguration;
import com.rany.cake.toolkit.lang.config.KitConfig;
import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.id.UUIds;
import com.rany.cake.toolkit.lang.json.Jsons;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Lists;
import com.rany.cake.toolkit.lang.utils.Strings;

import java.util.*;
import java.util.function.Supplier;

/**
 * rpc 远程调用结果封装
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2019/5/29 11:27
 */
public class RpcWrapper<T> extends CloneSupport<RpcWrapper<T>> implements Wrapper<T>, ILogObject, IMapObject<String, Object> {

    private static final long serialVersionUID = 7940497300629314L;

    public static final String RPC_SUCCESS_CODE = KitConfig.get(KitLangConfiguration.CONFIG.RPC_SUCCESS_CODE);

    public static final String RPC_SUCCESS_MESSAGE = KitConfig.get(KitLangConfiguration.CONFIG.RPC_SUCCESS_MESSAGE);

    public static final String RPC_ERROR_CODE = KitConfig.get(KitLangConfiguration.CONFIG.RPC_ERROR_CODE);

    public static final String RPC_ERROR_MESSAGE = KitConfig.get(KitLangConfiguration.CONFIG.RPC_ERROR_MESSAGE);

    public static final String PRC_TRACE_PREFIX = KitConfig.get(KitLangConfiguration.CONFIG.PRC_TRACE_PREFIX);

    /**
     * 状态码
     */
    @JSONField(ordinal = 0)
    private String code;

    /**
     * 信息对象
     */
    @JSONField(ordinal = 1)
    private String msg;

    /**
     * 结果对象
     */
    @JSONField(ordinal = 4)
    private T content;

    /**
     * 错误信息
     */
    @JSONField(ordinal = 3)
    private List<String> errorMessages;

    /**
     * 会话追踪标识
     */
    @JSONField(ordinal = 2)
    private String traceId;

    public RpcWrapper() {
        this(RPC_SUCCESS_CODE, RPC_SUCCESS_MESSAGE, null);
    }

    public RpcWrapper(String code) {
        this(code, RPC_SUCCESS_MESSAGE, null);
    }

    public RpcWrapper(String code, String msg) {
        this(code, msg, null);
    }

    public RpcWrapper(String code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
        this.traceId = this.createTrace();
    }

    public RpcWrapper(CodeInfo info) {
        this(info.code(), info.message(), null);
    }

    public RpcWrapper(CodeInfo info, T content) {
        this(info.code(), info.message(), content);
    }

    /**
     * 初始化
     */
    public static <T> RpcWrapper<T> get() {
        return new RpcWrapper<>();
    }

    /**
     * 定义
     */
    public static <T> RpcWrapper<T> of(CodeInfo info) {
        return new RpcWrapper<>(info.code(), info.message());
    }

    public static <T> RpcWrapper<T> of(CodeInfo info, T data) {
        return new RpcWrapper<>(info.code(), info.message(), data);
    }

    public static <T> RpcWrapper<T> of(String code) {
        return new RpcWrapper<>(code);
    }

    public static <T> RpcWrapper<T> of(String code, String msg, T data) {
        return new RpcWrapper<>(code, msg, data);
    }

    /**
     * 成功
     */
    public static <T> RpcWrapper<T> success() {
        return new RpcWrapper<>(RPC_SUCCESS_CODE, RPC_SUCCESS_MESSAGE);
    }

    public static <T> RpcWrapper<T> success(T data) {
        return new RpcWrapper<>(RPC_SUCCESS_CODE, RPC_SUCCESS_MESSAGE, data);
    }

    /**
     * 失败
     */
    public static <T> RpcWrapper<T> error() {
        return new RpcWrapper<>(RPC_ERROR_CODE, RPC_ERROR_MESSAGE);
    }

    public static <T> RpcWrapper<T> error(String code) {
        return new RpcWrapper<>(code, RPC_ERROR_MESSAGE);
    }

    public static <T> RpcWrapper<T> error(String msg, Object... params) {
        return new RpcWrapper<>(RPC_ERROR_CODE, Strings.format(msg, params));
    }

    public static <T> RpcWrapper<T> error(String code, String msg) {
        return new RpcWrapper<>(code, msg);
    }

    public static <T> RpcWrapper<T> error(String code, String msg, Object... params) {
        return new RpcWrapper<>(code, Strings.format(msg, params));
    }

    public static <T> RpcWrapper<T> error(Throwable t) {
        return new RpcWrapper<>(RPC_ERROR_CODE, t.getMessage());
    }

    public static <T> RpcWrapper<T> error(String code, Throwable t) {
        return new RpcWrapper<>(code, t.getMessage());
    }

    /**
     * 检查是否成功
     */
    @JSONField(serialize = false)
    public boolean isSuccess() {
        return !RPC_ERROR_CODE.equals(code) && Lists.isEmpty(errorMessages);
    }

    /**
     * 失败抛出异常
     */
    public void errorThrows() {
        this.errorThrows(null, (Object[]) null);
    }

    public void errorThrows(String msg) {
        this.errorThrows(msg, (Object[]) null);
    }

    public void errorThrows(String msg, Object... args) {
        if (!isSuccess()) {
            if (msg == null) {
                throw Exceptions.rpcWrapper(this);
            } else {
                throw Exceptions.rpcWrapper(this, Strings.format(msg, args));
            }
        }
    }

    public void errorThrows(Supplier<? extends RuntimeException> e) {
        if (!isSuccess()) {
            throw e.get();
        }
    }

    public RpcWrapper<T> addErrorMessage(String errorMsg) {
        if (errorMessages == null) {
            this.errorMessages = new ArrayList<>();
        }
        errorMessages.add(errorMsg);
        return this;
    }

    public RpcWrapper<T> code(String code) {
        this.code = code;
        return this;
    }

    public RpcWrapper<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public RpcWrapper<T> data(T data) {
        this.content = data;
        return this;
    }

    public RpcWrapper<T> trace(Object object) {
        this.traceId = PRC_TRACE_PREFIX + object;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public void setTraceId(String traceId) {
        this.traceId = PRC_TRACE_PREFIX + traceId;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getContent() {
        return content;
    }

    public String getTraceId() {
        return traceId;
    }

    public List<String> getErrorMessages() {
        return errorMessages == null ? errorMessages = new ArrayList<>() : errorMessages;
    }

    @JSONField(serialize = false)
    public String getErrorMessageString() {
        return errorMessages == null ? Strings.EMPTY : Lists.join(errorMessages, ",");
    }

    private String createTrace() {
        return this.traceId = PRC_TRACE_PREFIX + UUIds.random32();
    }

    @Override
    public String toString() {
        return this.toLogString();
    }

    @Override
    public String toLogString() {
        StringBuilder builder = new StringBuilder("RpcWrapper: ");
        boolean success = this.isSuccess();
        builder.append("\n  isSuccess ==> ").append(success)
                .append("\n    traceId ==> ").append(traceId)
                .append("\n       code ==> ").append(code)
                .append("\n        msg ==> ").append(msg)
                .append("\n       content ==> ").append(Jsons.toJsonWriteNull(content));
        if (!success) {
            builder.append("\n   errorMsg ==> ").append(errorMessages);
        }
        return builder.toString();
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(Const.CAPACITY_8);
        map.put("code", code);
        map.put("msg", msg);
        map.put("content", content);
        map.put("traceId", traceId);
        map.put("errorMessages", errorMessages);
        return map;
    }

    /**
     * @return {@link HttpWrapper}
     */
    public HttpWrapper<T> toHttpWrapper() {
        return HttpWrapper.of(code, msg, content);
    }

    /**
     * @return result 的 Optional
     */
    public Optional<T> optional() {
        return Optional.of(this)
                .filter(RpcWrapper::isSuccess)
                .map(RpcWrapper::getContent);
    }

}
