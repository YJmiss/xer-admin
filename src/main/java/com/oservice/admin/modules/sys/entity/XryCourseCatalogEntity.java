package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 课程目录表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course_catalog")
public class XryCourseCatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 目录ID
	 */
	private Long id;
	/**
	 * 目录名称
	 */
	@NotBlank(message="目录名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String title;

	/**
	 * 所属课程ID
	 */
	@NotBlank(message="课程ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long courseid;
	
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCourseid() {
		return courseid;
	}

	public void setCourseid(Long courseid) {
		this.courseid = courseid;
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
