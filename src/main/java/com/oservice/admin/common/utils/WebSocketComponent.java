package com.oservice.admin.common.utils;


import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送消息的组件
 */
@Component
public class WebSocketComponent {
    @Autowired
    @Resource
    /** SimpMessagingTemplate是SpringBoot提供操作WebSocket的对象 */
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 发送消息方法
     */
    public void sendMessage(XryMessageEntity message) {
        // 将消息推送给‘topic/ip’的客户端
        messagingTemplate.convertAndSend("/topic/ip", "我是从服务器来的消息!");
    }
}
