package com.rany.cake.toolkit.http.apache.file;

import com.rany.cake.toolkit.http.BaseHttpRequest;
import com.rany.cake.toolkit.http.apache.ApacheRequests;
import com.rany.cake.toolkit.http.apache.ApacheResponse;
import com.rany.cake.toolkit.http.apache.BaseApacheRequest;
import com.rany.cake.toolkit.http.support.HttpContentType;
import com.rany.cake.toolkit.http.support.HttpMethod;
import com.rany.cake.toolkit.http.support.HttpUploadPart;
import com.rany.cake.toolkit.lang.constant.StandardContentType;
import com.rany.cake.toolkit.lang.utils.Charsets;
import com.rany.cake.toolkit.lang.utils.Exceptions;
import com.rany.cake.toolkit.lang.utils.Lists;
import com.rany.cake.toolkit.lang.utils.Valid;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Apache Http 文件上传
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/6/13 1:03
 */
public class ApacheUpload extends BaseApacheRequest {

    /**
     * 文件体
     */
    private Collection<HttpUploadPart> parts;

    /**
     * 是否执行完毕
     */
    private volatile boolean done;

    public ApacheUpload(String url) {
        this(url, ApacheRequests.getClient());
    }

    public ApacheUpload(String url, CloseableHttpClient client) {
        this.url = url;
        this.client = client;
        this.method = HttpMethod.POST.method();
    }

    @Override
    public ApacheUpload method(HttpMethod method) {
        return this.method(method.method());
    }

    @Override
    public ApacheUpload method(String method) {
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
     */
    public ApacheUpload part(HttpUploadPart part) {
        this.parts = Lists.singleton(part);
        return this;
    }

    /**
     * 上传多文件
     *
     * @param parts ignore
     */
    public ApacheUpload parts(Collection<HttpUploadPart> parts) {
        this.parts = parts;
        return this;
    }

    @Override
    protected HttpEntity getEntry() {
        Valid.notEmpty(parts, "upload part is empty");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if (formParts != null) {
            formParts.forEach((k, v) -> {
                builder.addTextBody(k, v, ContentType.create(StandardContentType.TEXT_PLAIN, Charsets.of(charset)));
            });
        }
        for (HttpUploadPart part : parts) {
            String key = part.getParam();
            String contentType = part.getContentType();
            File file = part.getFile();
            String fileName = part.getFileName();
            byte[] bytes = part.getBytes();
            InputStream in = part.getIn();
            if (in != null) {
                builder.addBinaryBody(key, in, ContentType.parse(contentType), fileName);
            } else if (file != null) {
                builder.addBinaryBody(key, file, ContentType.parse(contentType), fileName);
            } else if (bytes != null) {
                builder.addBinaryBody(key, bytes, ContentType.parse(contentType), fileName);
            }
        }
        return builder.build();
    }

    @Override
    public ApacheResponse await() {
        super.buildRequest();
        try (CloseableHttpResponse resp = client.execute(request)) {
            return new ApacheResponse(url, resp);
        } catch (IOException e) {
            throw Exceptions.httpRequest(url, e);
        } finally {
            this.done = true;
            this.request.releaseConnection();
        }
    }

    public boolean isDone() {
        return done;
    }

}
