package com.oservice.admin.config;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/messageWebSocket")
@Component
public class MessageWebsocket {
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    private static CopyOnWriteArraySet<MessageWebsocket> messageWebsocket = new CopyOnWriteArraySet<MessageWebsocket>();
    /**
     * 连接成功
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("---------- 连接关闭 ----------");
    }

    /**
     * 收到消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println("来自浏览器的消息:" + message);
        for (MessageWebsocket item : messageWebsocket) {
            item.sendMessage(message);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("-------------发生错误-----------");
        error.printStackTrace();
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);//同步
        //this.session.getAsyncRemote().sendText(message);//异步
    }
}
