package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @program: oservice
 * @description: 订单对应课程表
 * @author: YJmiss
 * @create: 2018-12-19 09:58
 **/
@TableName("xry_order_course")
public class XryOrderCourseEntity implements Serializable {
    /*
     `id` varchar(20) COLLATE utf8_bin NOT NULL,
     `course_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '课程id',
     `order_id` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '订单id',
     `title` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '课程标题',
     `price` bigint(50) DEFAULT NULL COMMENT '课程单价',
     `pic_path` varchar(200) COLLATE utf8_bin DEFAULT NULL COMMENT '课程图片地址',
     */
    private String id;
    private long courseId;
    private String orderId;
    private String title;
    private long price;
    private String picPath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
