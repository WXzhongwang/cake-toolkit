package com.rany.cake.toolkit.http.ok.file;

import com.rany.cake.toolkit.http.BaseHttpRequest;
import com.rany.cake.toolkit.http.ok.BaseOkRequest;
import com.rany.cake.toolkit.http.ok.OkRequests;
import com.rany.cake.toolkit.http.ok.OkResponse;
import com.rany.cake.toolkit.http.support.HttpContentType;
import com.rany.cake.toolkit.http.support.HttpMethod;
import com.rany.cake.toolkit.http.support.HttpUploadPart;
import com.rany.cake.toolkit.lang.Asyncable;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Lists;
import com.rany.cake.toolkit.lang.utils.Valid;
import okhttp3.*;

import java.io.IOException;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * OkHttp 异步上传文件
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2021/3/8 15:35
 */
public class OkAsyncUpload extends BaseOkRequest implements Asyncable<Consumer<OkResponse>> {

    /**
     * 文件体
     */
    private Collection<HttpUploadPart> parts;

    /**
     * 异步执行失败是否抛出异常
     */
    private boolean asyncFailThrows;

    public OkAsyncUpload(String url) {
        this(url, OkRequests.getClient());
    }

    public OkAsyncUpload(String url, OkHttpClient client) {
        this.url = url;
        this.client = client;
        this.method = HttpMethod.POST.method();
    }

    public OkAsyncUpload asyncFailThrows(boolean asyncFailThrows) {
        this.asyncFailThrows = asyncFailThrows;
        return this;
    }

    public OkAsyncUpload asyncFailThrows() {
        this.asyncFailThrows = true;
        return this;
    }

    @Override
    public OkAsyncUpload method(HttpMethod method) {
        return this.method(method.method());
    }

    @Override
    public OkAsyncUpload method(String method) {
        this.method = method;
        if (super.isNoBodyRequest()) {
            throw Exceptions.unsupported("unsupported method " + method);
        }
        return this;
    }

    @Override
    public BaseHttpRequest body(String body) {
        throw Exceptions.unsupported("upload file unsupported set body");
    }

    @Override
    public BaseHttpRequest body(byte[] body) {
        throw Exceptions.unsupported("upload file unsupported set body");
    }

    @Override
    public BaseHttpRequest body(byte[] body, int offset, int len) {
        throw Exceptions.unsupported("upload file unsupported set body");
    }

    @Override
    public BaseHttpRequest contentType(String contentType) {
        throw Exceptions.unsupported("upload file unsupported set contentType");
    }

    @Override
    public BaseHttpRequest contentType(HttpContentType contentType) {
        throw Exceptions.unsupported("upload file unsupported set contentType");
    }

    /**
     * 上传单文件
     *
     * @param part ignore
     * @return this
     */
    public OkAsyncUpload part(HttpUploadPart part) {
        this.parts = Lists.singleton(part);
        return this;
    }

    /**
     * 上传多文件
     *
     * @param parts ignore
     * @return this
     */
    public OkAsyncUpload parts(Collection<HttpUploadPart> parts) {
        this.parts = parts;
        return this;
    }

    @Override
    protected void setBody(Request.Builder requestBuilder) {
        super.setMultipartBody(requestBuilder, parts);
    }

    @Override
    public void async(Consumer<OkResponse> callback) {
        Valid.notNull(callback, "async call back is null");
        super.buildRequest();
        this.call = client.newCall(request);
        OkResponse response = new OkResponse(url, tag);
        this.call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response res) {
                response.asyncSetResponse(res);
                callback.accept(response);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                response.error(e);
                if (asyncFailThrows) {
                    throw Exceptions.httpRequest(url, "async ok upload file on failure: " + OkAsyncUpload.super.getRequestMessage(), e);
                }
                callback.accept(response);
            }
        });
    }

}
