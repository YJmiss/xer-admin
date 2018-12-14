package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.enums.EventType;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.CustomMenuViewEventRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 自定义菜单查看事件示例代码
 * author:980463316@qq.com
 * Created on 2016-09-07 23:37.
 */
@Slf4j
@Component
@MessageProcessor(messageType = MessageType.EVENT, eventType = EventType.EVENT_CUSTOM_MENU_VIEW)
public class CustomMenuViewEventHandlerExample extends AbstractMessageHandler {
    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        CustomMenuViewEventRequestMessage customMenuViewEventRequestMessage = (CustomMenuViewEventRequestMessage) baseRequestMessage;

        //在这里实现你自己的业务逻辑
        log.info("{} 点击了[view]类型的菜单,eventKey={}", customMenuViewEventRequestMessage.getFromUserName(), customMenuViewEventRequestMessage.getEventKey());
        return null;

    }
}
