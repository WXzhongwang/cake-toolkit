package com.rany.cake.toolkit.cms.util;

import com.rany.cake.toolkit.cms.exception.CmsException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CmsHttpClient {

    private final CloseableHttpClient httpClient;

    private RequestConfig requestConfig;

    private static final Integer MAX_TIMEOUT = 3 * 1000;

    public CmsHttpClient(CloseableHttpClient httpClient) {
        this.httpClient = httpClient;
    }

    private void init() {
        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    public void destroy() throws IOException {
        httpClient.close();
    }


    /**
     * 发起http请求
     *
     * @param url 请求地址
     * @return response body里面的内容
     */
    public String doGet(String url) {
        HttpGet httpGet = new HttpGet(url);
        if (requestConfig == null) {
            init();
        }
        httpGet.setConfig(requestConfig);

        HttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, StandardCharsets.UTF_8);
                }
            }
            return null;
        } catch (IOException e) {
            throw new CmsException("请求oss出错", e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException ignore) {

                }
            }
        }
    }

}
