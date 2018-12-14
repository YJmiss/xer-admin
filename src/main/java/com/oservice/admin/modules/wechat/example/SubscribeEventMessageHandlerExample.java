package com.oservice.admin.modules.wechat.example;

import com.oservice.admin.modules.wechat.annotation.MessageProcessor;
import com.oservice.admin.modules.wechat.bean.GetUserInfoParameter;
import com.oservice.admin.modules.wechat.bean.WechatUser;
import com.oservice.admin.modules.wechat.enums.EventType;
import com.oservice.admin.modules.wechat.enums.Lang;
import com.oservice.admin.modules.wechat.enums.MessageType;
import com.oservice.admin.modules.wechat.handler.AbstractMessageHandler;
import com.oservice.admin.modules.wechat.message.request.BaseRequestMessage;
import com.oservice.admin.modules.wechat.message.request.SubscribeEventRequestMessage;
import com.oservice.admin.modules.wechat.message.response.Article;
import com.oservice.admin.modules.wechat.message.response.BaseResponseMessage;
import com.oservice.admin.modules.wechat.service.user.WechatUserService;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 事件消息处理器示例代码
 * <p/>
 * User: jonnyliu@tcl.com <br/>
 * Date: on 2016-08-22 13:47.
 */
@Slf4j
@Component
@MessageProcessor(messageType = MessageType.EVENT, eventType = EventType.EVENT_SUBSCRIBE)
public class SubscribeEventMessageHandlerExample extends AbstractMessageHandler {

    @Autowired
    private WechatUserService wechatUserService;

    @Override
    public BaseResponseMessage doHandleMessage(BaseRequestMessage baseRequestMessage) {
        //关注事件消息
        SubscribeEventRequestMessage subOrUnSubEventRequestMessage = (SubscribeEventRequestMessage) baseRequestMessage;
        String fromUserName = subOrUnSubEventRequestMessage.getFromUserName();
        WechatUser wechatUserInfo = this.wechatUserService.getWechatUserInfo(new GetUserInfoParameter(fromUserName, Lang.CHINESE.getLanguageCode()));
        String title = "你好！感谢您的关注！";
        if (wechatUserInfo == null) {
            return MessageUtils.buildTextResponseMessage(baseRequestMessage, title);
        }
        title = "亲爱的:" + wechatUserInfo.getNickname() + "," + title;
        Article article = new Article();
        article.setUrl("https://github.com/151376liujie/wechat-core");
        article.setTitle(title);
        article.setPicUrl(wechatUserInfo.getHeadimgurl());
        article.setDescription("微信开发框架，封装了微信消息发送和接收的细节，用户只需关注自己业务本身，并且支持注解开发。");
        List<Article> list = new ArrayList<>(1);
        list.add(article);
        Map<String, String> param = new HashMap<>();
        param.put("ArticleCount", String.valueOf(list.size()));
        return MessageUtils.buildNewsResponseMessage(baseRequestMessage, param, list);
    }
}
