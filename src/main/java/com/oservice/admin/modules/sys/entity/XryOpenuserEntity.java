package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 系统用户
 * 第三方登录用户表的实体类
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_openuser")
public class XryOpenuserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 第三方登录用户id
     */
    private String id;
    /**
     * 用户表主键
     */
    private String userId;
    /**
     * 第三方类型(qq,微信，支付宝)
     */
    private String opentype;
    /**
     * 代表用户唯一身份的ID
     */
    private String openId;
    /**
     * 授权过期时间
     */
    private String expiredTime;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 头像图片
     */
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpentype() {
        return opentype;
    }

    public void setOpentype(String opentype) {
        this.opentype = opentype;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
