package com.rany.cake.toolkit.alert.alert.model;


import com.rany.cake.toolkit.alert.alert.utils.SignUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author zhongshengwang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingActionCardAlertEntity extends BaseDingAlertEntity {

    /**
     * 首屏会话透出的展示内容
     */
    private String title;

    /**
     * markdown格式的消息
     */
    private String text;

    /**
     * 单个按钮的方案。(设置此项和singleURL后btns无效)
     */
    private String singleTitle;

    /**
     * 点击singleTitle按钮触发的URL
     */
    private String singleURL;

    /**
     * 0-按钮竖直排列，1-按钮横向排列
     */
    private String btnOrientation;

    /**
     * 0-正常发消息者头像，1-隐藏发消息者头像
     */
    private String hideAvatar;

    public DingActionCardAlertEntity(WebHookParam webHookParam) {
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
    }
}
