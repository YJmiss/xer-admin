package com.oservice.admin.modules.wechat.share.init;

import com.oservice.admin.modules.wechat.share.thread.AccessTokenThread;
import com.oservice.admin.modules.wechat.share.thread.JsApiTicketThread;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * 容器启动就会运行这个类，获取access_token和jsp_ticket
 * <p>Title:InitAccessToken</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:38:33
 */
@Component
public class InitParameters implements ApplicationRunner {

    @Value("${WX_APPID}")
    private String WX_APPID;
    @Value("${WX_APPSECRET}")
    private String WX_APPSECRET;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("WX_APPID=" + WX_APPID);
        System.out.println("WX_APPSECRET=" + WX_APPSECRET);
        // 设置appid和appsecret属性值
        AccessTokenThread.appid = WX_APPID;
        AccessTokenThread.appsecret = WX_APPSECRET;

        // log.info("weixin api appid :" + TokenThread.appid);
        // log.info("weixin api appsecret:" + TokenThread.appsecret);

        if ("".equals(AccessTokenThread.appid) || "".equals(AccessTokenThread.appsecret)) {
            // log.error("appid和appsecret未给出");
            System.out.println("appid和appsecret未给出");
        } else {
            new Thread(new AccessTokenThread()).start();
            new Thread(new JsApiTicketThread()).start();
            // System.out.println("accesstoken初始化成功");
        }
    }
}
