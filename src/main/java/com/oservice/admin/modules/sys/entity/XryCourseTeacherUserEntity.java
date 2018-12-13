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
 * 课程报名和讲师关注表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course_teacher_user")
@ApiModel(value = "课程对象")
public class XryCourseTeacherUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * ID
	 */
	private Long id;

	/**
	 * 类型（1：报名课程；2：讲师关注）
	 */
	private Integer type;

	/**
	 * 关注的用户
	 */
	private String userId;

	/**
	 * 用户报名的课程id
	 */
	private Long courseId;

	/**
	 * 关注讲师的id
	 */
	private String teacherId;

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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}
}
