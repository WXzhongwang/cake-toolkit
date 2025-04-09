package com.rany.cake.toolkit.alert.alert.model;

import lombok.Data;


/**
 * @author zhongshengwang
 */
@Data
public class WebHookParam {

    private String webHookUrl;

    /**
     * 只有当加签useSign设置为true时候，secret才生效
     */
    private Boolean useSign;

    private String secret;

    public WebHookParam(String webHookUrl, Boolean useSign, String secret) {
        this.webHookUrl = webHookUrl;
        this.useSign = useSign;
        this.secret = secret;
    }
}
