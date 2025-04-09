package com.rany.cake.toolkit.alert.alert.model;


import com.rany.cake.toolkit.alert.alert.enums.DingTalkMsgTypeEnum;
import com.rany.cake.toolkit.alert.alert.utils.SignUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


/**
 * @author zhongshengwang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingMarkDownAlertEntity extends BaseDingAlertEntity {

    private String title;

    private String text;

    /**
     * at 用户手机号
     */
    private Set<String> mobiles;

    /**
     * 是否at所有用户
     */
    private Boolean isAtAll;

    public DingMarkDownAlertEntity(WebHookParam webHookParam, String title, String text) {
        this.msgType = DingTalkMsgTypeEnum.MARKDOWN;
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
        this.title = title;
        this.text = text;
    }

}
