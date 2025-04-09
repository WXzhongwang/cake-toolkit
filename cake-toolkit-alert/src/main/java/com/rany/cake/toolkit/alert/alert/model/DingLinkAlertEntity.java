package com.rany.cake.toolkit.alert.alert.model;


import com.rany.cake.toolkit.alert.alert.enums.DingTalkMsgTypeEnum;
import com.rany.cake.toolkit.alert.alert.utils.SignUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author zhongshengwang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingLinkAlertEntity extends BaseDingAlertEntity {

    private String title;

    private String text;

    private String msgUrl;

    private String picUrl;

    public DingLinkAlertEntity(WebHookParam webHookParam, String title, String text, String msgUrl) {
        this.msgType = DingTalkMsgTypeEnum.LINK;
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
        this.title = title;
        this.text = text;
        this.msgUrl = msgUrl;
    }

    public DingLinkAlertEntity(WebHookParam webHookParam, String title, String text, String msgUrl, String picUrl) {
        this.msgType = DingTalkMsgTypeEnum.LINK;
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
        this.title = title;
        this.text = text;
        this.msgUrl = msgUrl;
        this.picUrl = picUrl;
    }

}
