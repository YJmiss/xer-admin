package com.oservice.admin.modules.app.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @program: oservice
 * @description: 购物车实体类
 * @author: YJmiss
 * @create: 2018-12-18 14:42
 **/
public class AppCartEntity implements Serializable {
    /**
     * 课程ID
     */
    private Long id;
    /**
     * 课程标题
     */
    private String title;
    /**
     * 课程图片
     */
    private String image;
    /**
     * 课程价格 默认0，单位为：分
     */
    private Long price;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 课程价格 默认0，单位为：元
     */
    private String appPrice;
    /**
     * 分享人ID
     */
    private String sharingId;

    public String getSharingId() {
        return sharingId;
    }

    public void setSharingId(String sharingId) {
        this.sharingId = sharingId;
    }

    public String getAppPrice() {
        return new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString();
    }

    public void setAppPrice(String appPrice) {
        this.appPrice = appPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
