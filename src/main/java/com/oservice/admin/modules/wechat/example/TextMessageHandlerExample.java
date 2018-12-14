package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.TextRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import org.springframework.stereotype.Component;

/**
 * 文本消息处理器helloworld示例
 * <p/>
 * User: jonnyliu@tcl.com <br/>
 * Date: on 2016-08-19 10:07.
 */
@Component
@MessageProcessor(messageType = MessageType.TEXT_MESSAGE)
public class TextMessageHandlerExample extends AbstractMessageHandler {

    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        System.out.println(Thread.currentThread().getName());
        TextRequestMessage textRequestMessage = (TextRequestMessage) baseRequestMessage;
        return MessageUtils.buildTextResponseMessage(baseRequestMessage, textRequestMessage.getContent());
    }
}
