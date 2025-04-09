package com.rany.cake.toolkit.alert.alert.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.rany.cake.toolkit.alert.alert.DingAlertService;
import com.rany.cake.toolkit.alert.alert.manager.DingTalkAlertManager;
import com.rany.cake.toolkit.alert.alert.model.*;

import java.util.List;

/**
 * @author zhongshengwang
 * 通过钉钉机器人发送钉钉消息推送到群里
 * <p>
 * 消息发送频率限制：每个机器人每分钟最多发送20条
 * <p>
 * 每个机器人每分钟最多发送20条。消息发送太频繁会严重影响群成员的使用体验，大量发消息的场景 (譬如系统监控报警) 可以将这些信息进行整合，
 * 通过markdown消息以摘要的形式发送到群里。
 */
public class DingAlertServiceImpl implements DingAlertService {

    @Override
    public boolean sendDingTalkTextMsg(DingTextAlertEntity alertEntity) {
        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_TEXT);
        JSONObject text = new JSONObject();
        String format = String.format("告警等级:[%s]: %s", alertEntity.getLevel().name(), alertEntity.getContent());
        text.put(DingConstants.TEXT_REQ_KEY_CONTENT, format);
        request.put(DingConstants.DING_REQ_KEY_TEXT, text);
        request.put(DingConstants.TEXT_REQ_KEY_AT, new JSONObject()
                .fluentPut(DingConstants.TEXT_REQ_KEY_AT_ALL, alertEntity.getIsAtAll())
                .fluentPut(DingConstants.TEXT_REQ_KEY_AT_MOBILES, alertEntity.getMobiles()));
        return alertEntity.isAsync() ? DingTalkAlertManager.asyncAlert(alertEntity.getWebHookUrl(), request)
                : DingTalkAlertManager.alert(alertEntity.getWebHookUrl(), request);
    }

    @Override
    public boolean sendDingTalkLinkMsg(DingLinkAlertEntity alertEntity) {
        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_LINK);
        JSONObject link = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_LINK, link);
        link.fluentPut(DingConstants.DING_REQ_KEY_TEXT, alertEntity.getText())
                .fluentPut(DingConstants.LINK_REQ_KEY_TITLE, alertEntity.getTitle())
                .fluentPut(DingConstants.LINK_REQ_KEY_PIC_URL, alertEntity.getPicUrl())
                .fluentPut(DingConstants.LINK_REQ_KEY_MESSAGE_URL, alertEntity.getMsgUrl());
        return alertEntity.isAsync() ? DingTalkAlertManager.asyncAlert(alertEntity.getWebHookUrl(), request)
                : DingTalkAlertManager.alert(alertEntity.getWebHookUrl(), request);
    }

    @Override
    public boolean sendDingTalkMarkDownMsg(DingMarkDownAlertEntity alertEntity) {
        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_MARKDOWN);
        JSONObject markdown = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MARKDOWN, markdown);
        markdown.put(DingConstants.MARKDOWN_REQ_KEY_TITLE, alertEntity.getTitle());
        markdown.put(DingConstants.MARKDOWN_REQ_KEY_TEXT, alertEntity.getText());
        request.put(DingConstants.MARKDOWN_REQ_KEY_AT, new JSONObject()
                .fluentPut(DingConstants.MARKDOWN_REQ_KEY_AT_ALL, alertEntity.getIsAtAll())
                .fluentPut(DingConstants.MARKDOWN_REQ_KEY_AT_MOBILES, alertEntity.getMobiles()));
        return alertEntity.isAsync() ? DingTalkAlertManager.asyncAlert(alertEntity.getWebHookUrl(), request)
                : DingTalkAlertManager.alert(alertEntity.getWebHookUrl(), request);
    }

    @Override
    public boolean sendDingTalkActionCardMsg(DingActionCardAlertEntity alertEntity) {
        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_ACTION_CARD);
        JSONObject actionCard = new JSONObject();
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_TITLE, alertEntity.getTitle());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_TEXT, alertEntity.getText());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_HIDE_AVATAR, alertEntity.getHideAvatar());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_BTN_ORIENTATION, alertEntity.getBtnOrientation());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_SINGLE_TITLE, alertEntity.getSingleTitle());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_SINGLE_URL, alertEntity.getSingleURL());
        request.put(DingConstants.DING_REQ_KEY_ACTION_CARD, actionCard);
        return alertEntity.isAsync() ? DingTalkAlertManager.asyncAlert(alertEntity.getWebHookUrl(), request)
                : DingTalkAlertManager.alert(alertEntity.getWebHookUrl(), request);
    }

    @Override
    public boolean sendDingTalkMultiActionCardMsg(DingMultiActionCardAlertEntity alertEntity) {
        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_ACTION_CARD);
        JSONObject actionCard = new JSONObject();
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_TITLE, alertEntity.getTitle());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_TEXT, alertEntity.getText());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_HIDE_AVATAR, alertEntity.getHideAvatar());
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_BTN_ORIENTATION, alertEntity.getBtnOrientation());
        JSONArray buttons = new JSONArray();
        actionCard.put(DingConstants.ACTION_CARD_REQ_KEY_BTN, buttons);
        List<ActionCardBtnItem> actions = alertEntity.getButtons();
        for (ActionCardBtnItem button : actions) {
            JSONObject btn = new JSONObject();
            btn.put(DingConstants.ACTION_CARD_REQ_KEY_BTN_TITLE, button.getTitle());
            btn.put(DingConstants.ACTION_CARD_REQ_KEY_BTN_ACTION_URL, button.getActionUrl());
        }
        request.put(DingConstants.DING_REQ_KEY_ACTION_CARD, actionCard);
        return alertEntity.isAsync() ? DingTalkAlertManager.asyncAlert(alertEntity.getWebHookUrl(), request)
                : DingTalkAlertManager.alert(alertEntity.getWebHookUrl(), request);
    }

    @Override
    public boolean sendDingTalkFeedCardMsg(DingFeedCardAlertEntity alertEntity) {
        JSONObject request = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_MSG_TYPE, DingConstants.DING_MSG_TYPE_FEED_CARD);
        JSONObject feedCard = new JSONObject();
        request.put(DingConstants.DING_REQ_KEY_FEED_CARD, feedCard);
        JSONArray links = new JSONArray();
        feedCard.put(DingConstants.FEED_CARD_REQ_KEY_LINKS, links);
        List<FeedCardItem> feeds = alertEntity.getFeeds();
        for (FeedCardItem item : feeds) {
            JSONObject feed = new JSONObject();
            feed.fluentPut(DingConstants.FEED_CARD_REQ_KEY_TITLE, item.getTitle());
            feed.fluentPut(DingConstants.FEED_CARD_REQ_KEY_MESSAGE_URL, item.getMessageURL());
            feed.fluentPut(DingConstants.FEED_CARD_REQ_KEY_PIC_URL, item.getPicURL());
            links.add(feed);
        }
        return alertEntity.isAsync() ? DingTalkAlertManager.asyncAlert(alertEntity.getWebHookUrl(), request)
                : DingTalkAlertManager.alert(alertEntity.getWebHookUrl(), request);
    }
}
