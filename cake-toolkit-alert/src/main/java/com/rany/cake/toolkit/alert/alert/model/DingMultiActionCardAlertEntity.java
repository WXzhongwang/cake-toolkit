package com.rany.cake.toolkit.alert.alert.model;

import com.rany.cake.toolkit.alert.alert.utils.SignUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @author zhongshengwang
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class DingMultiActionCardAlertEntity extends BaseDingAlertEntity {

    /**
     * 首屏会话透出的展示内容
     */
    private String title;

    /**
     * markdown格式的消息
     */
    private String text;

    public List<ActionCardBtnItem> buttons;

    /**
     * 0-按钮竖直排列，1-按钮横向排列
     */
    private String btnOrientation;

    /**
     * 0-正常发消息者头像，1-隐藏发消息者头像
     */
    private String hideAvatar;

    public DingMultiActionCardAlertEntity(WebHookParam webHookParam) {
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
    }
}
