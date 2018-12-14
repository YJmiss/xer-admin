package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.constant.WechatConstant;
import com.oservice.admin.modules.wechat.enums.EventType;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.PhotoMenuEventRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 接收系统拍照发图菜单事件示例代码
 * author:980463316@qq.com
 * Created on 2016-09-07 23:37.
 */
@Slf4j
@Component
@MessageProcessor(messageType = MessageType.EVENT, eventType = EventType.EVENT_PIC_SYS_PHOTO)
public class SystemPhotoMenuEventHandlerExample extends AbstractMessageHandler {

    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {

        PhotoMenuEventRequestMessage photoMenuEventRequestMessage = (PhotoMenuEventRequestMessage) baseRequestMessage;

        String eventKey = photoMenuEventRequestMessage.getEventKey();

        //用户点击了系统拍照发图菜单
        if (WechatConstant.MENU_PIC_SYS_PHOTO.equalsIgnoreCase(eventKey)) {
            if (log.isInfoEnabled()) {
                log.info("{} 点击了系统拍照发图菜单,发送的图片信息为:{}", photoMenuEventRequestMessage.getFromUserName(), photoMenuEventRequestMessage.getSendPicsInfo());
            }
            return MessageUtils.buildTextResponseMessage(baseRequestMessage, "您点击了[系统拍照发图菜单]");
        }

        return null;

    }
}
