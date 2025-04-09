package com.rany.cake.toolkit.alert.alert.model;


import com.rany.cake.toolkit.alert.alert.enums.DingTalkMsgTypeEnum;
import com.rany.cake.toolkit.alert.alert.utils.SignUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/**
 * @author zhongshengwang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DingFeedCardAlertEntity extends BaseDingAlertEntity {

    private List<FeedCardItem> feeds;

    public DingFeedCardAlertEntity(WebHookParam webHookParam, List<FeedCardItem> feeds) {
        this.webHookUrl = SignUtils.getAlertUrl(webHookParam);
        this.msgType = DingTalkMsgTypeEnum.FEED_CARD;
        this.feeds = feeds;
    }
}
