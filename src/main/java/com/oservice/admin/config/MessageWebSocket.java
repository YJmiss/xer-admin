package com.oservice.admin.config;

import com.oservice.admin.common.utils.EncoderWebSocket;
import com.oservice.admin.common.utils.JsonUtil;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/messageWebSocket")
@Component
public class MessageWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private Session session;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MessageWebSocket> messageWebsocket = new CopyOnWriteArraySet<MessageWebSocket>();

    /**
     * 连接建立成功调用的方法
     * 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        messageWebsocket.add(this);
        System.out.println("this:" + this);
        System.out.println("-----------session初始化-----------");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        messageWebsocket.remove(this);  //从set中删除
        System.out.println("---------- 连接关闭 ----------");
    }

    /**
     * 服务器接收客户端的消息
     *
     * @param message
     */
    @OnMessage
    public void onMessage(String message, Session session) throws Exception {
        System.out.println("来自浏览器的消息:" + message);
        for (MessageWebSocket item : messageWebsocket) {
            System.out.println(session);
            messageWebsocket.add(item);
            // item.sendMessage(msg);
        }
    }

    /**
     * 服务器向客户端发送消息
     * @param message
     * @throws Exception
     */
    public void sendMessage(String message) {
        try {
            for (MessageWebSocket item : messageWebsocket) {
                System.out.println("item.session:" + item.session);
                item.session.getBasicRemote().sendText(message);
                System.out.println("数据发送成功:" + message);
            }
        } catch (IOException e) {
            System.out.println(e);
            messageWebsocket.remove(this);
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

}
