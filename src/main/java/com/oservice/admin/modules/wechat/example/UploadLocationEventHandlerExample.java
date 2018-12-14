package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.enums.EventType;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.UploadLocationEventRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import org.springframework.stereotype.Component;

/**
 * 用户上传地理位置信息事件类型
 * author:980463316@qq.com
 * Created on 2016-09-07 23:21.
 */
@Component
@MessageProcessor(messageType = MessageType.EVENT, eventType = EventType.EVENT_UPLOAD_LOCATION)
public class UploadLocationEventHandlerExample extends AbstractMessageHandler {

    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        UploadLocationEventRequestMessage uploadLocationEventRequestMessage = (UploadLocationEventRequestMessage) baseRequestMessage;
        //在这里实现你自己的业务逻辑

        return null;
    }
}
