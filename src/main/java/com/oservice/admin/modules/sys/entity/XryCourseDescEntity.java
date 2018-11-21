package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 课程目录表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course_desc")
@ApiModel(value = "课程描述对象")
public class XryCourseDescEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 课程ID
	 */
	private Long courseId;
	/**
	 * 课程描述
	 */
	private String courseDesc;

	/**
	 * 创建时间
	 */
	private Date created;
	/**
	 * 更新时间
	 */
	private Date updated;

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getCourseDesc() {
		return courseDesc;
	}

	public void setCourseDesc(String courseDesc) {
		this.courseDesc = courseDesc;
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
