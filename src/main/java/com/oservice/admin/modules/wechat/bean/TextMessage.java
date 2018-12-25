package com.oservice.admin.modules.wechat.bean;

import java.util.Date;

/**
 * @Description: 文本消息
 * @Author: xiewl
 * @param:
 * @Date: 2018/9/11 13:51
 * @Version 1.0
 */
public class TextMessage {
    private String FromUserName;
    private String ToUserName;
    private String MsgType;
    private Date CreateTime;

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    private String Content;
}
