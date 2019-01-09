package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: oservice
 * @description: 分销订单
 * @author: YJmiss
 * @create: 2019-01-09 15:44
 **/
@TableName("distribution_order")
public class DistributionOrder implements Serializable {
    /*
    `id` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单id',
    `order_id` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单id',
    `user_id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '用户id',
    `course_id` bigint(20) unsigned NOT NULL COMMENT '课程id',
    `grade` int(5) NOT NULL COMMENT '用户分销等级 1:一级 2:二级',
    `brokerage` bigint(20) DEFAULT NULL COMMENT '佣金金额，单位：分',
    `status` int(5) NOT NULL COMMENT '状态：1、未付款，2、未提现(分享用户支付订单成功)，3、交易成功(已提现，平台支付成功)',
    `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
    `end_time` datetime DEFAULT NULL COMMENT '交易成功时间',
    */
    private String id;
    private String orderId;
    private String userId;
    private long courseId;
    private int grade;
    private long brokerage;
    private int status;
    private Date createTime;
    private Date updateTime;
    private Date endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public long getBrokerage() {
        return brokerage;
    }

    public void setBrokerage(long brokerage) {
        this.brokerage = brokerage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
