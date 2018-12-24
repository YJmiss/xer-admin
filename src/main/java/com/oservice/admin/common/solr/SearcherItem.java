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
    private String realName;
    private String courseDesc;
    private Integer applicantCount;
    private Integer feedback;
    public SearcherItem() {
        super();
    }

    public SearcherItem(String id, String title, long price, String image, String categoryName, String realName, String courseDesc, Integer applicantCount, Integer feedback) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.categoryName = categoryName;
        this.realName = realName;
        this.courseDesc = courseDesc;
        this.applicantCount = applicantCount;
        this.feedback = feedback;
    }

    public SearcherItem(String id, String title, long price, String image, String cid,
                        String categoryName, String realName, String courseDesc) {
        super();
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.cid = cid;
        this.categoryName = categoryName;
        this.realName = realName;
        this.courseDesc = courseDesc;
    }

    public String[] getImages() {
        return this.image.split(",");

    }

    public Integer getApplicantCount() {
        return applicantCount;
    }

    public void setApplicantCount(Integer applicantCount) {
        this.applicantCount = applicantCount;
    }

    public Integer getFeedback() {
        return feedback;
    }

    public void setFeedback(Integer feedback) {
        this.feedback = feedback;
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

    public String getRealName() {
        return realName;
    }

    public void setNickname(String realName) {
        this.realName = realName;
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
