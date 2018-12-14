package com.oservice.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
// EnableWebSocketMessageBroker注解开启使用STOMP协议来传输基于代理(message broker)的消息
// 这时控制器支持使用@MessageMapping,就像使用@RequestMapping一样
//@EnableWebSocketMessageBroker
/*public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { */
public class WebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    /*@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // registry.addEndpoint("/messageWebSocket")添加一个访问端点“/messageWebSocket”,客户端打开双通道时需要的url
        // setAllowedOrigins("*")设置跨域
        // withSockJS()指定SockJS协议
        registry.addEndpoint("/messageWebSocket").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 配置消息代理(message broker)
        // 将消息传回给以"/topic"开头的客户端
        // 点对点应配置一个/xxx消息代理config.enableSimpleBroker("/topic","/xxx")
        // 广播式应配置一个/topic消息代理
        config.enableSimpleBroker("/topic");
        //点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/xxx/
        // config.setUserDestinationPrefix("/xxx");

    }*/
}