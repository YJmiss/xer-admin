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
 * 课程类目表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course_cat")
@ApiModel(value = "课程类目对象")
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
	private Long parent_id;
	/**
	 * 类目名称
	 */
	@NotBlank(message="类目名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;

	/**
	 * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	 */
	private Integer sort_order;

	/**
	 * 该类目是否为父类目，1为true，0为false
	 */
	private boolean is_parent;

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


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort_order() {
		return sort_order;
	}

	public void setSort_order(Integer sort_order) {
		this.sort_order = sort_order;
	}

	public boolean isIs_parent() {
		return is_parent;
	}

	public void setIs_parent(boolean is_parent) {
		this.is_parent = is_parent;
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
