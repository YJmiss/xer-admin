package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 讲师表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_teacher")
@ApiModel(value = "讲师对象")
public class XryTeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 讲师ID
	 */
	private Long id;
	/**
	 * 讲师认证的用户
	 */
	private String userId;
	/**
	 * 真实名字
	 */
	private String realName;
	/**
	 * 身份证号
	 */
	private String idCard;
	/**
	 * 身份证正面
	 */
	private String idCardFront;
	/**
	 * 身份证反面
	 */
	private String idCardBack;
	/**
	 * 认证类型（1：个人认证  2：机构认证）
	 */
	private Integer type;
	/**
	 * 所属机构（没有可不填）
	 */
	private Long orgId;
	/**
	 *  认证状态(1：认证中 2：未通过： 3：已通过）
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date created;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}