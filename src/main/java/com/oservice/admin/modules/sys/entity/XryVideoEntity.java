package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 视频表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_video")
public class XryVideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 视频id，同时也是视频编号
	 */
	private Long id;
	/**
	 * 视频标题
	 */
	@NotBlank(message="视频标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String title;
	/**
	 * 视频路径
	 */
	private String videoUrl;
	/**
	 * 所属课程ID
	 */
	private Long courseId;
	/**
	 * 所属目录ID
	 */
	private Long catalogId;
	/**
	 * 视频属性，1-试学，2-收费，3-免费
	 */
	private Integer property;
	/**
	 * 视频状态，1-未审核，2-审核中，3-通过审核，4-未通过
	 */
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
	 * 参数数据，格式为json格式
	 */
	private String  paramData;


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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

	public Integer getProperty() {
		return property;
	}

	public void setProperty(Integer property) {
		this.property = property;
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

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}
}
