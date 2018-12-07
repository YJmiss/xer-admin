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
 * 推荐表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_recommend")
public class XryRecommendEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 课程ID
	 */
	private Long id;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 父类目id（多选，~分割）
	 */
	private String parentId;

	/**
	 * 子类目id（多选，~分割）
	 */
	private String catId;

	/**
	 * 课程标题
	 */
	private String catTitle;

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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCatId() {
		return catId;
	}

	public void setCatId(String catId) {
		this.catId = catId;
	}

	public String getCatTitle() {
		return catTitle;
	}

	public void setCatTitle(String catTitle) {
		this.catTitle = catTitle;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}

