package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.enums.EventType;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.UnsubscribeEventRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 取消关注事件处理器示例代码
 * author:980463316@qq.com
 * Created on 2016-09-07 23:11.
 */
@Slf4j
@Component
@MessageProcessor(messageType = MessageType.EVENT, eventType = EventType.EVENT_UNSUBSCRIBE)
public class UnsubscribeEventHandlerExample extends AbstractMessageHandler {

    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        UnsubscribeEventRequestMessage unSubEventRequestMessage = (UnsubscribeEventRequestMessage) baseRequestMessage;
        String fromUserName = unSubEventRequestMessage.getFromUserName();
        if (log.isWarnEnabled()) {
            log.warn("用户：[{}] 取消了对公众号的关注！", fromUserName);
        }
        //取消关注时清空微信用户的笑话浏览记录
        return null;
    }
}
