package com.oservice.admin.modules.wechat.share.pojo;

/**
 * 由AppId和AppSecret获得
 * <p>Title:AccessToken</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:03:00
 */
public class AccessToken implements java.io.Serializable {
    // 接口访问凭证
    private String accessToken;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public AccessToken() {

    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

}
