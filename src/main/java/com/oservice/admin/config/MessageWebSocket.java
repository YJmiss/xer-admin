package com.oservice.admin.config;

import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/messageWebSocket")
@Component
public class MessageWebSocket {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private Session session;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static CopyOnWriteArraySet<MessageWebSocket> messageWebsocket = new CopyOnWriteArraySet<MessageWebSocket>();
    private static ConcurrentHashMap<String, MessageWebSocket> webSocketSet = new ConcurrentHashMap<>();
    private String token = "";

    /**
     * 连接建立成功调用的方法
     * 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        this.token = session.getQueryString().split("=")[1];
        messageWebsocket.add(this);
        webSocketSet.put(this.token, this);
        sendMessage("-----------连接成功-----------");
        System.out.println("-----------连接成功-----------");
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
        }
    }

    /**
     * 服务器向客户端发送消息
     * 广播式
     *
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
     * 给指定ID用户发送信息
     *
     * @param message
     * @param tokenList
     * @throws IOException
     */
    public void sendToUser(String message, List<String> tokenList) throws IOException {
        if (null != tokenList) {
            if (tokenList.size() > 0) {
                for (String token : tokenList) {
                    if (null != webSocketSet.get(token)) {
                        webSocketSet.get(token).sendMessage(message);
                    }
                }
            }
        }
    }

    /**
     * 在线的用户的发送消息
     *
     * @param message
     * @param userIds
     * @throws IOException
     */
    public void sendToOnLineUser(String message, List<String> userIds) throws IOException {
        if (null != userIds) {
            if (userIds.size() > 0) {
                for (Map.Entry<String, MessageWebSocket> e : webSocketSet.entrySet()) {
                    String userId = e.getValue().session.getQueryString().split("=")[1];
                    if (userIds.size() > 0) {
                        for (String sendUserId : userIds) {
                            if (userId.equals(sendUserId)) {
                                webSocketSet.get(sendUserId).sendMessage(message);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        messageWebsocket.remove(this);
        webSocketSet.remove(this.token);
        System.out.println("---------- 连接关闭 ----------");
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        webSocketSet.remove(this.token);
        error.printStackTrace();
        System.out.println("-------------发生错误-----------");
    }

}
