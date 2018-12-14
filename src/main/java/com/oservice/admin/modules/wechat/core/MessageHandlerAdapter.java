package com.oservice.admin.modules.wechat.core;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.bean.MessageHandlerElement;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;

/**
 * 消息分发器，根据消息的不同来将消息发送给不同的MessageWorker<br/>
 * Created on 2016/8/6 14:05.
 *
 * @author 980463316@qq.com
 * @see MessageProcessor 消息处理器注解<br/>
 */
public interface MessageHandlerAdapter {

    /**
     * 将不同类型的消息发送给对应的消息处理器
     *
     * @param msgType   用户发送给公众号的消息类型
     * @param eventType 消息事件类型
     * @return 对应的消息处理器
     */
    AbstractMessageHandler findMessageHandler(MessageHandlerElement messageHandlerElement);

}
