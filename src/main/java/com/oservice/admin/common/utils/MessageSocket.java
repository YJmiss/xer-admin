package com.oservice.admin.common.utils;

import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/messageSocket")
@Component
public class MessageSocket {
    private Session session;

    private static CopyOnWriteArraySet<MessageSocket> messageSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        messageSockets.add(this);
        System.out.println("【messageSocket消息】有新的连接, 总数:{}" + messageSockets.size());
    }

    @OnClose
    public void onClose() {
        messageSockets.remove(this);
        System.out.println("【messageSocket消息】连接断开, 总数:{}" + messageSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("【messageSocket消息】收到客户端发来的消息:{}" + message);
    }

    /**
     * 发送消息
     * @param message
     */
    public void sendMessage(XryMessageEntity message){
        for (MessageSocket messageSocket: messageSockets) {
            System.out.println("【messageSocket消息】广播消息, message={}" + message);
            try {
                messageSocket.session.getBasicRemote().sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
