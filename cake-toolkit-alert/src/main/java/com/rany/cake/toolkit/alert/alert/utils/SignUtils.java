package com.rany.cake.toolkit.alert.alert.utils;

import com.rany.cake.toolkit.alert.alert.model.WebHookParam;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author zhongshengwang
 */
public class SignUtils {

    private final static Logger logger = LoggerFactory.getLogger(SignUtils.class);

    public static String getSign(long timestamp, String secret) {
        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes(StandardCharsets.UTF_8));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            logger.error("get sign error", e);
        }
        return "";
    }

    public static String getAlertUrl(WebHookParam webHookParam) {
        if (!webHookParam.getUseSign()) {
            return webHookParam.getWebHookUrl();
        }
        long timestamp = System.currentTimeMillis();
        String sign = getSign(timestamp, webHookParam.getSecret());
        return String.format("%s&timestamp=%s&sign=%s", webHookParam.getWebHookUrl(), timestamp, sign);
    }

    public static void main(String[] args) throws Exception {
        WebHookParam webHookParam = new WebHookParam("https://oapi.dingtalk.com/robot/send?access_token=b729a1e337c48afeef8f1f400aa75b08f8dc1f3366f0000223a6bef79a01532a",
                true, "SEC2ac0e12b0e49330361d9f80bd98cfed198d48897c8b7f00a2d35925161f3ef35");
        System.out.println(getAlertUrl(webHookParam));
    }
}
