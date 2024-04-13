package com.rany.cake.toolkit.lang.wrapper;


import com.alibaba.fastjson.annotation.JSONField;
import com.rany.cake.toolkit.lang.CloneSupport;
import com.rany.cake.toolkit.lang.KitLangConfiguration;
import com.rany.cake.toolkit.lang.config.KitConfig;
import com.rany.cake.toolkit.lang.constant.Const;
import com.rany.cake.toolkit.lang.utils.Objects1;
import com.rany.cake.toolkit.lang.utils.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * restful 结果封装
 *
 * @author zhongwang
 * @date 2024/2/2
 * @slogan Why Not
 * @email 18668485565@163.com
 */
public class HttpWrapper<T> extends CloneSupport<HttpWrapper<T>> implements Wrapper<T>, ILogObject, IMapObject<String, Object> {

    private static final long serialVersionUID = 7048691672612601L;

    // -------------------- HTTP --------------------

    public static final String HTTP_OK_CODE = KitConfig.get(KitLangConfiguration.CONFIG.HTTP_OK_CODE);

    public static final String HTTP_OK_MESSAGE = KitConfig.get(KitLangConfiguration.CONFIG.HTTP_OK_MESSAGE);

    public static final String HTTP_ERROR_CODE = KitConfig.get(KitLangConfiguration.CONFIG.HTTP_ERROR_CODE);

    public static final String HTTP_ERROR_MESSAGE = KitConfig.get(KitLangConfiguration.CONFIG.HTTP_ERROR_MESSAGE);

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
    @JSONField(ordinal = 2)
    private T content;

    public HttpWrapper() {
        this(HTTP_OK_CODE, HTTP_OK_MESSAGE, null);
    }

    public HttpWrapper(String code) {
        this(code, HTTP_OK_MESSAGE, null);
    }

    public HttpWrapper(String code, String msg) {
        this(code, msg, null);
    }

    public HttpWrapper(String code, String msg, T content) {
        this.code = code;
        this.msg = msg;
        this.content = content;
    }

    public HttpWrapper(CodeInfo info) {
        this(info.code(), info.message(), null);
    }

    public HttpWrapper(CodeInfo info, T content) {
        this(info.code(), info.message(), content);
    }

    /**
     * 初始化
     */
    public static <T> HttpWrapper<T> get() {
        return new HttpWrapper<>();
    }

    /**
     * 定义
     */
    public static <T> HttpWrapper<T> of(CodeInfo info) {
        return new HttpWrapper<>(info.code(), info.message());
    }

    public static <T> HttpWrapper<T> of(CodeInfo info, T data) {
        return new HttpWrapper<>(info.code(), info.message(), data);
    }

    public static <T> HttpWrapper<T> of(String code, String msg) {
        return new HttpWrapper<>(code, msg);
    }

    public static <T> HttpWrapper<T> of(String code, String msg, T data) {
        return new HttpWrapper<>(code, msg, data);
    }

    /**
     * 成功
     */
    public static <T> HttpWrapper<T> ok() {
        return new HttpWrapper<>(HTTP_OK_CODE, HTTP_OK_MESSAGE);
    }

    public static <T> HttpWrapper<T> ok(T data) {
        return new HttpWrapper<>(HTTP_OK_CODE, HTTP_OK_MESSAGE, data);
    }

    /**
     * 失败
     */
    public static <T> HttpWrapper<T> error() {
        return new HttpWrapper<>(HTTP_ERROR_CODE, HTTP_ERROR_MESSAGE);
    }

    public static <T> HttpWrapper<T> error(String code) {
        return new HttpWrapper<>(code, HTTP_ERROR_MESSAGE);
    }

    public static <T> HttpWrapper<T> error(String msg, Object... params) {
        return new HttpWrapper<>(HTTP_ERROR_CODE, Strings.format(msg, params));
    }

    public static <T> HttpWrapper<T> error(String code, String msg) {
        return new HttpWrapper<>(code, msg);
    }

    public static <T> HttpWrapper<T> error(String code, String msg, Object... params) {
        return new HttpWrapper<>(code, Strings.format(msg, params));
    }

    public static <T> HttpWrapper<T> error(Throwable t) {
        return new HttpWrapper<>(HTTP_ERROR_CODE, t.getMessage());
    }

    public static <T> HttpWrapper<T> error(String code, Throwable t) {
        return new HttpWrapper<>(code, t.getMessage());
    }

    public HttpWrapper<T> code(String code) {
        this.code = code;
        return this;
    }

    public HttpWrapper<T> msg(String msg) {
        this.msg = msg;
        return this;
    }

    public HttpWrapper<T> data(T data) {
        this.content = data;
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

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getContent() {
        return content;
    }

    /**
     * 是否为成功状态码
     *
     * @return 是否成功
     */
    @JSONField(serialize = false)
    public boolean isOk() {
        return HTTP_OK_CODE.equals(code);
    }

    @Override
    public String toString() {
        return this.toLogString();
    }

    @Override
    public String toLogString() {
        return "HttpWrapper:" +
                "\n  code ==> " + code +
                "\n   msg ==> " + msg +
                "\n  content ==> " + Objects1.toString(content);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>(Const.CAPACITY_8);
        map.put("code", code);
        map.put("msg", msg);
        map.put("content", content);
        return map;
    }

    /**
     * @return {@link RpcWrapper}
     */
    public RpcWrapper<T> toRpcWrapper() {
        return new RpcWrapper<>(code, msg, content);
    }

    /**
     * @return result 的 Optional
     */
    public Optional<T> optional() {
        return Optional.of(this)
                .filter(HttpWrapper::isOk)
                .map(HttpWrapper::getContent);
    }

}
