package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 学而用用户表的实体类
 *
 * @author LingDu
 * @version 1.0
 */
@TableName("xry_user")
public class XryUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * app用户ID
     */
    @TableId
    private Long id;

    /**
     * 账号
     */
    private String usercode;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 密码
     */
    private String password;

    /**
     * 个人简介
     */
    private String intro;

    /**
     * 头像图片
     */
    private String avatar;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    /**
     * 盐
     */
    private String salt;

    /**
     * 登录令牌
     */
    private String loginToken;

    /**
     * 刷新登录令牌
     */
    private String refreshToken;

    /**
     * 第三方登录来源(0->手机, 1->微信, 2->QQ,3->支付宝)
     */
    private Integer socialSource;

    /**
     * 第三方登录用户主键
     */
    private String openuserId;

    /**
     * 创建日期
     */
    private Date created;

    /**
     * 更新日期
     */
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsercode() {
        return usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getSocialSource() {
        return socialSource;
    }

    public void setSocialSource(Integer socialSource) {
        this.socialSource = socialSource;
    }

    public String getOpenuserId() {
        return openuserId;
    }

    public void setOpenuserId(String openuserId) {
        this.openuserId = openuserId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
