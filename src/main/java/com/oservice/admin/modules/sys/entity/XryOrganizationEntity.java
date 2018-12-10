package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 机构表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_organization")
@ApiModel(value = "机构对象")
public class XryOrganizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 机构ID
	 */
	private Long id;
	/**
	 * 机构认证的用户
	 */
	private String userId;
	/**
	 * 机构名称
	 */
	private String orgName;
	/**
	 * 机构代码
	 */
	private String orgCode;
	/**
	 *  法人
	 */
	private String corporator;
	/**
	 * 身份证号码
	 */
	private String idCard;
	/**
	 * 联系号码
	 */
	private String contact;
	/**
	 * 营业执照
	 */
	private String businessLicense;
	/**
	 * 认证状态(1：认证中 2：未通过： 3：已通过）
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

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCorporator() {
		return corporator;
	}

	public void setCorporator(String corporator) {
		this.corporator = corporator;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
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
