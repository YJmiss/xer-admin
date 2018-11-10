package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 课程类目表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course_cat")
public class XryCourseCatEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 类目ID
	 */
	private Long id;
	/**
	 * 父类目ID=0时，代表的是一级的类目
	 */
	@NotBlank(message="父类目ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long parentId;
	/**
	 * 类目名称
	 */
	@NotBlank(message="类目名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;

	/**
	 * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	 */
	private Integer sortOrder;

	/**
	 * 该类目是否为父类目，1为true，0为false
	 */
	private boolean isParent;

	/**
	 * 状态。可选值:1(正常),2(删除)
	 */
	@NotBlank(message="请确认视频状态", groups = {AddGroup.class, UpdateGroup.class})
	private Integer status;

	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 更新时间
	 */
	private Date updated;

	/**
	 * ztree属性
	 */
	@TableField(exist=false)
	private Boolean open;

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean parent) {
		isParent = parent;
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

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}
}
