package com.rany.cake.toolkit.alert.alert.manager;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author zhongshengwang
 */
public class DingUtils {

    private final static Logger logger = LoggerFactory.getLogger(DingUtils.class);

    public static boolean send(DingMsgRequest dingMsgRequest) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
                // 设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)
                // 设置读取超时时间
                .build();
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(contentType, dingMsgRequest.getBody().toJSONString());
        Request request =
                new Request.Builder().url(dingMsgRequest.getUrl())
                        .post(body)
                        .addHeader("cache-control", "no-cache").build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                assert response.body() != null;
                String responseContent = response.body().string();
                logger.debug(responseContent);
            }
            return true;
        } catch (IOException e) {
            logger.error("发送机器人消息：{}", e.getMessage(), e);
        }
        return false;
    }
}
