package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 第三方登录用户
 *
 * @author YJmiss
 * @version 1.0
 */
@TableName("xry_openuser")
public class XryOpenUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId
    private String id;
    /**
     * 用户表ID
     */
    private String userId;
    /**
     * 第三方类型:微信
     */
    private String openType;
    /**
     * 用户唯一身份的ID
     */
    private String openId;
    /**
     * 授权过期时间
     */
    private String expiredTime;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 头像图片
     */
    private String avatar;
    /**
     * 获取：用户ID
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：用户ID
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取：用户表ID
     */
    public String getUserId() {
        return userId;
    }
    /**
     * 设置：用户表ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    /**
     * 获取：第三方类型
     */
    public String getOpenType() {
        return openType;
    }
    /**
     * 设置：第三方类型
     */
    public void setOpenType(String openType) {
        this.openType = openType;
    }
    /**
     * 获取：用户唯一身份的ID
     */
    public String getOpenId() {
        return openId;
    }
    /**
     * 设置：用户唯一身份的ID
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }
    /**
     * 获取：授权过期时间
     */
    public String getExpiredTime() {
        return expiredTime;
    }
    /**
     * 设置：授权过期时间
     */
    public void setExpiredTime(String expiredTime) {
        this.expiredTime = expiredTime;
    }
    /**
     * 获取：昵称
     */
    public String getNickName() {
        return nickName;
    }
    /**
     * 设置：昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    /**
     * 获取：头像图片
     */
    public String getAvatar() {
        return avatar;
    }
    /**
     * 设置：头像图片
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
