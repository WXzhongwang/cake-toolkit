package com.rany.cake.toolkit.http.ok.file;

import com.rany.cake.toolkit.http.ok.OkRequest;
import com.rany.cake.toolkit.http.ok.OkResponse;
import com.rany.cake.toolkit.lang.io.Files1;
import com.rany.cake.toolkit.lang.io.Streams;
import okhttp3.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * OkHttp 下载文件
 *
 * @author zhongshengwang
 * @version 1.0.0
 * @since 2020/6/25 0:15
 */
public class OkDownload {

    private final OkRequest request;

    private OkResponse response;

    public OkDownload(String url) {
        this(new OkRequest(url), null);
    }

    public OkDownload(String url, OkHttpClient client) {
        this(new OkRequest(url), client);
    }

    public OkDownload(OkRequest request) {
        this(request, null);
    }

    public OkDownload(OkRequest request, OkHttpClient client) {
        this.request = request;
        if (client != null) {
            this.request.client(client);
        }
    }

    public OkDownload client(OkHttpClient client) {
        request.client(client);
        return this;
    }

    public OkDownload download(String file) throws IOException {
        Files1.touch(file);
        this.download(Files1.openOutputStream(file), true);
        return this;
    }

    public OkDownload download(File file) throws IOException {
        Files1.touch(file);
        this.download(Files1.openOutputStream(file), true);
        return this;
    }

    public OkDownload download(OutputStream out) throws IOException {
        this.download(out, false);
        return this;
    }

    public OkDownload download(OutputStream out, boolean autoClose) throws IOException {
        this.response = request.await();
        out.write(response.getBody());
        if (autoClose) {
            Streams.close(out);
        }
        return this;
    }

    public OkRequest getRequest() {
        return request;
    }

    public OkResponse getResponse() {
        return response;
    }

}
