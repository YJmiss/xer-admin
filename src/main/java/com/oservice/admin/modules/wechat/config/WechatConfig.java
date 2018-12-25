package com.oservice.admin.modules.wechat.config;

import com.oservice.admin.modules.wechat.common.service.WeixinAuthorizeService;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * 微信的配置项
 * author:980463316@qq.com <br/>
 * Created on 2016-08-25 20:36.
 */
@Repository
public class WechatConfig implements Serializable {
    @Resource
    private WeixinAuthorizeService weixinAuthorizeService;

    private String appId;

    private String appsecret;

    private String token;

    private String encodingAESKey;

    public String getAppId() {
        return weixinAuthorizeService.getAuthorize(1).getAppId();
    }

    public String getAppsecret() {
        return weixinAuthorizeService.getAuthorize(1).getAppSecret();
    }

    public String getToken() {
        return "miss0you";
    }

    public String getEncodingAESKey() {
        return "q3ZRrY518TCWE1FaMmOhsiTxKQnlsI6P9vUjdgNR54I";
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setEncodingAESKey(String encodingAESKey) {
        this.encodingAESKey = encodingAESKey;
    }
}
