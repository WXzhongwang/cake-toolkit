package com.rany.cake.toolkit.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.rany.cake.toolkit.lang.IHttpResponse;
import com.rany.cake.toolkit.lang.utils.Valid;

/**
 * http 请求
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2022/7/8 16:47
 */
public abstract class BaseHttpResponse implements IHttpResponse {

    /**
     * 获取 body
     *
     * @return body
     */
    public abstract byte[] getBody();

    /**
     * 获取 body string
     *
     * @return body string
     */
    public abstract String getBodyString();

    /**
     * 验证是否成功
     */
    public void validOk() {
        Valid.validHttpOk(this);
    }

    /**
     * 获取 json body
     *
     * @param ref ref
     * @param <T> T
     * @return json
     */
    public <T> T getJsonObjectBody(TypeReference<T> ref) {
        this.validOk();
        return JSON.parseObject(this.getBodyString(), ref);
    }

}
