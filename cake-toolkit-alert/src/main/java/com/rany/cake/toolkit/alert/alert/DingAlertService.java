package com.rany.cake.toolkit.alert.alert;


import com.rany.cake.toolkit.alert.alert.model.*;

/**
 * 钉钉群聊消息接口
 *
 * @author zhongshengwang
 */
public interface DingAlertService {


    /**
     * 发送DingTalk文本消息
     *
     * @param alertEntity 告警实体
     * @return boolean 是否发送成功
     */
    boolean sendDingTalkTextMsg(DingTextAlertEntity alertEntity);


    /**
     * 发送链接类型消息
     *
     * @param alertEntity 告警实体
     * @return boolean 是否发送成功
     */
    boolean sendDingTalkLinkMsg(DingLinkAlertEntity alertEntity);

    /**
     * 发送markdown类型消息
     *
     * @param alertEntity 告警实体
     * @return boolean 是否发送成功
     */
    boolean sendDingTalkMarkDownMsg(DingMarkDownAlertEntity alertEntity);


    /**
     * 发送整体跳转 actionCard 类型消息
     *
     * @param alertEntity 告警实体
     * @return boolean 是否发送成功
     */
    boolean sendDingTalkActionCardMsg(DingActionCardAlertEntity alertEntity);

    /**
     * 发送独立跳转 actionCard 类型消息
     *
     * @param alertEntity 告警实体
     * @return boolean 是否发送成功
     */
    boolean sendDingTalkMultiActionCardMsg(DingMultiActionCardAlertEntity alertEntity);

    /**
     * 发送feedCard类型消息
     *
     * @param alertEntity 告警实体
     * @return boolean 是否发送成功
     */
    boolean sendDingTalkFeedCardMsg(DingFeedCardAlertEntity alertEntity);


}
