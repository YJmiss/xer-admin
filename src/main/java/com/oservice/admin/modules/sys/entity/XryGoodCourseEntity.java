package com.oservice.admin.modules.sys.entity;

import java.io.Serializable;

/**
 * @program: oservice
 * @description: 好评好课
 * @author: YJmiss
 * @create: 2018-12-10 17:41
 **/
public class XryGoodCourseEntity implements Serializable {
    private Long id;
    private String title;
    private String image;
    private Long price;
    /**
     * 星级评分：2、4、6、8、10'
     */
    private Integer score;

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public Long getPrice() {
        return price;
    }

    public Integer getScore() {
        return score;
    }
}
