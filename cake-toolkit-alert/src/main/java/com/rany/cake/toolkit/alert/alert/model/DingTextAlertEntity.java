package com.rany.cake.toolkit.alert.alert.model;

import com.rany.cake.toolkit.alert.alert.enums.DingTalkMsgTypeEnum;
import com.rany.cake.toolkit.alert.alert.enums.Level;
import com.rany.cake.toolkit.alert.alert.utils.SignUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;


/**
 * @author zhongshengwang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingTextAlertEntity extends BaseDingAlertEntity {

    protected Level level;
    /**
     * 告警内容
     */
    private String content;

    /**
     * at 用户手机号
     */
    private Set<String> mobiles;

    /**
     * 是否at所有用户
     */
    private Boolean isAtAll;

    public DingTextAlertEntity(WebHookParam webHookParam, String content, Level level) {
        this.msgType = DingTalkMsgTypeEnum.TEXT;
        this.level = level;
        this.content = content;
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
    }

    public DingTextAlertEntity(WebHookParam webHookParam, String content, Level level, Set<String> mobiles, Boolean isAtAll) {
        this.msgType = DingTalkMsgTypeEnum.TEXT;
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
        this.level = level;
        this.content = content;
        this.mobiles = mobiles;
        this.isAtAll = isAtAll;
    }

}
