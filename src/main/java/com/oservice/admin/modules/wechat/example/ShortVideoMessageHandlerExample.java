package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.VideoRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import org.springframework.stereotype.Component;

/**
 * 短视频消息接收和响应的code example
 * <p/>
 * User: jonnyliu@tcl.com <br/>
 * Date: on 2016-08-19 11:42.
 */
@Component
@MessageProcessor(messageType = MessageType.SHORT_VIDEO_MESSAGE)
public class ShortVideoMessageHandlerExample extends AbstractMessageHandler {
    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        //在这里实现你自己的业务逻辑
        VideoRequestMessage videoRequestMessage = (VideoRequestMessage) baseRequestMessage;
        String mediaId = videoRequestMessage.getMediaId();
        String thumbMediaId = videoRequestMessage.getThumbMediaId();
        String content = "您发送的短视频mediaId:%s \t,ThumbMediaId:%s ";
        content = String.format(content, mediaId, thumbMediaId);
        return MessageUtils.buildTextResponseMessage(baseRequestMessage, content);
    }
}
