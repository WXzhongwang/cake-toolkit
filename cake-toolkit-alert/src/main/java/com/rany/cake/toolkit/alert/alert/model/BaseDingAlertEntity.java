package com.rany.cake.toolkit.alert.alert.model;

import com.rany.cake.toolkit.alert.alert.enums.DingTalkMsgTypeEnum;
import lombok.Data;

import java.io.Serializable;


/**
 * @author zhongshengwang
 */
@Data
public abstract class BaseDingAlertEntity implements Serializable {

    protected String webHookUrl;

    protected DingTalkMsgTypeEnum msgType;

    protected boolean async = false;

}
