package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.constant.WechatConstant;
import com.oservice.admin.modules.wechat.enums.EventType;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.ScanCodeEventRequestMessage;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 扫码带提示事件处理器
 * Author: jonny
 * Time: 2017-08-18 14:48.
 */
@Slf4j
@Component
@MessageProcessor(messageType = MessageType.EVENT, eventType = EventType.EVENT_SCAN_CODE_WAIT_MSG)
public class ScanCodeWaitMsgEventHandlerExample extends AbstractMessageHandler {

    /**
     * 真正的处理消息的方法
     *
     * @param baseRequestMessage
     * @return
     */
    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {

        ScanCodeEventRequestMessage scanCodeEventRequestMessage = (ScanCodeEventRequestMessage) baseRequestMessage;
        String eventKey = scanCodeEventRequestMessage.getEventKey();
        if (eventKey.equalsIgnoreCase(WechatConstant.MENU_SCAN_CODE_WAIT_MSG)) {
            if (log.isInfoEnabled()) {
                log.info("{} 扫描二维码的结果是: {}", scanCodeEventRequestMessage.getFromUserName(), scanCodeEventRequestMessage.getScanCodeInfo());
            }
            String template = "您的扫描结果是:%s";
            return MessageUtils.buildTextResponseMessage(baseRequestMessage, String.format(template, scanCodeEventRequestMessage.getScanCodeInfo().getScanResult()));
        }

        return null;
    }
}
