package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.LinkRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import org.springframework.stereotype.Component;

/**
 * 接收链接消息的code example
 * <p/>
 * User: jonnyliu@tcl.com <br/>
 * Date: on 2016-08-19 13:09.
 */
@Component
@MessageProcessor(messageType = MessageType.LINK_MESSAGE)
public class LinkMessageHandlerExample extends AbstractMessageHandler {
    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        //在这里实现你自己的业务逻辑
        LinkRequestMessage linkRequestMessage = (LinkRequestMessage) baseRequestMessage;
        String content = "您发送的链接消息如下：title:%s,url:%s,description:%s ";
        content = String.format(content, linkRequestMessage.getTitle(), linkRequestMessage.getUrl(), linkRequestMessage.getDescription());
        return MessageUtils.buildTextResponseMessage(baseRequestMessage, content);
    }
}
