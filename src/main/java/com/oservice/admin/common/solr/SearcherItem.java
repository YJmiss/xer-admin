package com.oservice.admin.common.solr;

import java.io.Serializable;

/**
 * @program: oservice
 * @description: 搜索展示
 * @author: YJmiss
 * @create: 2018-12-04 09:52
 **/
public class SearcherItem implements Serializable {
    private String id;
    private String title;
    private long price;
    private String image;
    private String cid;
    private String categoryName;
    private String nickname;
    private String courseDesc;

    public SearcherItem() {
        super();
    }

    public SearcherItem(String id, String title, long price, String image, String categoryName, String nickname, String courseDesc) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.categoryName = categoryName;
        this.nickname = nickname;
        this.courseDesc = courseDesc;
    }

    public SearcherItem(String id, String title, long price, String image, String cid,
                        String categoryName, String nickname, String courseDesc) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.cid = cid;
        this.categoryName = categoryName;
        this.nickname = nickname;
        this.courseDesc = courseDesc;
    }

    public String[] getImages() {
        return this.image.split(",");

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public String getImage() {
        return image;
    }

    public String getCid() {
        return cid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCourseDesc() {
        return courseDesc;
    }

    public void setCourseDesc(String courseDesc) {
        this.courseDesc = courseDesc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
