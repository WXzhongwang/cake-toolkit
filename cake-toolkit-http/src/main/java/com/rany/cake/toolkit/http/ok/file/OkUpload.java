package com.rany.cake.toolkit.http.ok.file;

import com.rany.cake.toolkit.http.BaseHttpRequest;
import com.rany.cake.toolkit.http.ok.BaseOkRequest;
import com.rany.cake.toolkit.http.ok.OkRequests;
import com.rany.cake.toolkit.http.ok.OkResponse;
import com.rany.cake.toolkit.http.support.HttpContentType;
import com.rany.cake.toolkit.http.support.HttpMethod;
import com.rany.cake.toolkit.http.support.HttpUploadPart;
import com.rany.cake.toolkit.lang.Awaitable;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Lists;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Collection;

/**
 * OkHttp 上传文件
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/4/9 14:09
 */
public class OkUpload extends BaseOkRequest implements Awaitable<OkResponse> {

    /**
     * 文件体
     */
    private Collection<HttpUploadPart> parts;

    /**
     * 是否执行完毕
     */
    private volatile boolean done;

    public OkUpload(String url) {
        this(url, OkRequests.getClient());
    }

    public OkUpload(String url, OkHttpClient client) {
        this.url = url;
        this.client = client;
        this.method = HttpMethod.POST.method();
    }

    @Override
    public OkUpload method(HttpMethod method) {
        return this.method(method.method());
    }

    @Override
    public OkUpload method(String method) {
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
    public OkUpload part(HttpUploadPart part) {
        this.parts = Lists.singleton(part);
        return this;
    }

    /**
     * 上传多文件
     *
     * @param parts ignore
     * @return this
     */
    public OkUpload parts(Collection<HttpUploadPart> parts) {
        this.parts = parts;
        return this;
    }

    @Override
    protected void setBody(Request.Builder requestBuilder) {
        super.setMultipartBody(requestBuilder, parts);
    }

    @Override
    public OkResponse await() {
        super.buildRequest();
        this.call = client.newCall(request);
        try (Response resp = call.execute()) {
            return new OkResponse(url, tag, resp);
        } catch (IOException e) {
            throw Exceptions.httpRequest(url, "ok request upload file on failure: " + super.getRequestMessage(), e);
        } finally {
            this.done = true;
        }
    }

    public boolean isDone() {
        return done;
    }

}
