package com.oservice.admin.modules.wechat.message.request;

import com.oservice.admin.modules.wechat.enums.MessageType;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

/**
 * Created by jonnyliu-ds8 on 2016/8/5.
 */
@Data
@XStreamAlias("xml")
public class LinkRequestMessage extends CommonRequestMessage {

    /**
     * 消息标题
     */
    @XStreamAlias("Title")
    private String title;

    /**
     * 消息描述
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 消息链接
     */
    @XStreamAlias("Url")
    private String url;

    @Override
    public String getMsgType() {
        return MessageType.LINK_MESSAGE.getTypeStr();
    }

}
