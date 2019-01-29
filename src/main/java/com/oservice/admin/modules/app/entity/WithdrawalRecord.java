package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * @program: oservice
 * @description: 提现记录表
 * @author: YJmiss
 * @create: 2019-01-29 09:36
 **/
@TableName("withdrawal_record")
public class WithdrawalRecord implements Serializable {
    /*  `id` varchar(36) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '记录id',
              `user_name` varchar(10) COLLATE utf8_bin NOT NULL COMMENT '用户真实姓名',
              `user_id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '用户id',
              `mobile` bigint(20) unsigned NOT NULL COMMENT '用户手机号',
              `type` int(5) NOT NULL COMMENT '银行卡类型1：中国银行2：中国农业银行3：中国工商银行4：中国建设银行',
              `cashWithdrawal_amount` bigint(20) DEFAULT NULL COMMENT '提现金额单位：分',
              `status` int(5) NOT NULL COMMENT '状态：0、未申请，1、申请提现(用户发起提现申请)，2、提现成功(已提现，平台支付成功)',
              `create_time` datetime DEFAULT NULL COMMENT '记录创建时间',
              `end_time` datetime DEFAULT NULL COMMENT '交易成功时间',
      PRIMARY KEY (`id`),
      KEY `create_time` (`create_time`),
      KEY `user_id` (`user_id`),
      KEY `status` (`status`)
              ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;*/
    private String id;
    private String userName;
    private String userId;
    private Long mobile;
    private int type;
    private Long cashWithdrawalAmount;
    private int status;
    private Date createTime;
    private Date endTime;
    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Long getCashWithdrawalAmount() {
        return cashWithdrawalAmount;
    }

    public void setCashWithdrawalAmount(Long cashWithdrawalAmount) {
        this.cashWithdrawalAmount = cashWithdrawalAmount;
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

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
