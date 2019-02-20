package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 线下支付表
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_pay_offline")
@ApiModel(value = "线下支付表")
public class XryPayOfflineEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private String xuId;

	/**
	 * 课程id
	 */
	private Long xcId;

	/**
	 * 操作管理员id
	 */
	private String suId;

	/**
	 * 实际支付金额
	 */
	private String realPay;

	/**
	 * 创建时间
	 */
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getXuId() {
		return xuId;
	}

	public void setXuId(String xuId) {
		this.xuId = xuId;
	}

	public Long getXcId() {
		return xcId;
	}

	public void setXcId(Long xcId) {
		this.xcId = xcId;
	}

	public String getSuId() {
		return suId;
	}

	public void setSuId(String suId) {
		this.suId = suId;
	}

	public String getRealPay() {
		return realPay;
	}

	public void setRealPay(String realPay) {
		this.realPay = realPay;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
