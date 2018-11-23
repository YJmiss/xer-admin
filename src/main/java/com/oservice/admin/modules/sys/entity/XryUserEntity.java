package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户
 *
 * @author LingDu
 * @version 1.0
 */
@TableName("xry_user")
@ApiModel(value = "APP用户对象")
public class XryUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId
    private String id;
    /**
     * 用户状态：0：正常，1：异常，2：删除
     */
    private int status;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 手机号
     */
    private String phone;
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
     * 邮箱
     */
    private String email;
    /**
     *角色--0:普通用户 1：讲师
     */
    private int role;
    /**
     *登录到期时间
     */
    private String refreshToken;

    /**
     *第三方登录来源(0->手机, 1->微信
     */
    private int socialSource;

    /**
     *第三方登录登录用户主键
     */
    private String openUserId;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;

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
     * 获取：用户状态
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置：用户状态
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 获取：昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置：昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取：手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置：手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取：密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置：密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取：个人简介
     */
    public String getIntro() {
        return intro;
    }

    /**
     * 设置：个人简介
     */
    public void setIntro(String intro) {
        this.intro = intro;
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

    /**
     * 获取：注册邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置：注册邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取：角色
     */
    public int getRole() {
        return role;
    }

    /**
     * 设置：角色
     */
    public void setRole(int salt) {
        this.role = role;
    }

    /**
     * 获取：登录到期时间
     */
    public String getRefreshToken() {
        return refreshToken;
    }

    /**
     * 设置：刷新登录令牌
     */
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * 获取：第三方登录来源
     */
    public int getSocialSource() {
        return socialSource;
    }

    /**
     * 设置：第三方登录来源
     */
    public void setSocialSource(int socialSource) {
        this.socialSource = socialSource;
    }

    /**
     * 获取：第三方登录用户主键
     */
    public String getOpenUserId() {
        return openUserId;
    }

    /**
     * 设置：第三方登录用户主键
     */
    public void setOpenUserId(String openUserId) {
        this.openUserId = openUserId;
    }

    /**
     * 获取：创建日期
     */
    public Date getCreated() {
        return created;
    }

    /**
     * 设置：创建日期
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * 获取：更新日期
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * 设置：更新日期
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
