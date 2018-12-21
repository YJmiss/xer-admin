package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: oservice
 * @description: 订单实体
 * @author: YJmiss
 * @create: 2018-12-19 09:24
 **/
@TableName("xry_order")
public class XryOrderEntity implements Serializable {
    /*`
    order_id` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '订单id',
    `payment` varchar(50) COLLATE utf8_bin DEFAULT NULL COMMENT '实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分',
    `total_fee` bigint(50) DEFAULT NULL COMMENT '课程总金额',
    `payment_type` int(2) DEFAULT NULL COMMENT '支付类型，1、微信支付，2、支付宝支付..',
    `status` int(10) DEFAULT NULL COMMENT '状态：1、未付款，2、已付款，3、交易成功，4、交易关闭',
    `create_time` datetime DEFAULT NULL COMMENT '订单创建时间',
    `update_time` datetime DEFAULT NULL COMMENT '订单更新时间',
    `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
    `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
    `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
    `user_id` varchar(36) COLLATE utf8_bin DEFAULT NULL COMMENT '用户id',
    `buyer_phone` varchar(18) COLLATE utf8_bin DEFAULT NULL COMMENT '买家电话',
    */
    private String orderId;
    private String payment;
    private long totalFee;
    private int paymentType;
    private int status;
    private Date createTime;
    private Date updateTime;
    private Date paymentTime;
    private Date endTime;
    private Date closeTime;
    private String userId;
    private String buyerPhone;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
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

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }
}
