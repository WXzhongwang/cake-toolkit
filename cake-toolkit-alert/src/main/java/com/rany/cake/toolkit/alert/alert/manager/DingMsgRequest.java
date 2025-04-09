package com.rany.cake.toolkit.alert.alert.manager;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;


/**
 * @author zhongshengwang
 */
@Data
public class DingMsgRequest {

    private String url;

    private JSONObject body;

    public DingMsgRequest(String url, JSONObject body) {
        this.url = url;
        this.body = body;
    }
}
