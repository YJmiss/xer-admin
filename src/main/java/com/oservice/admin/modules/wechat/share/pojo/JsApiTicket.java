package com.oservice.admin.modules.wechat.share.pojo;

/**
 * 由access_token获得
 * <p>Title:JsApiTicket</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:03:21
 */
public class JsApiTicket implements java.io.Serializable {
    // 接口访问凭证
    private String ticket;
    // 凭证有效期，单位：秒
    private int expiresIn;

    public JsApiTicket() {

    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

}
