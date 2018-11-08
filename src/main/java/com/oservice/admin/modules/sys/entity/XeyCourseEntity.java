package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 课程表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course")
public class XeyCourseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 课程ID
	 */
	private Long id;
	/**
	 * 课程标题
	 */
	@NotBlank(message="课程标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String title;
	/**
	 * 课程图片
	 */
	private String image;
	/**
	 * 所属课程类目ID
	 */
	@NotBlank(message="课程标题不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private Long cid;
	/**
	 * 所属讲师ID
	 */
	private Long tid;
	/**
	 * 课程属性：1-收费，2-免费
	 */
	@NotBlank(message="请选择课程属性", groups = {AddGroup.class, UpdateGroup.class})
	private Integer property;
	/**
	 * 视频状态，1-未审核，2-审核中，3-通过审核，4-未通过
	 */
	@NotBlank(message="请确认视频状态", groups = {AddGroup.class, UpdateGroup.class})
	private Integer status;
	/**
	 * 课程价格 默认0，单位为：分
	 */
	@NotBlank(message="请填写课程价格", groups = {AddGroup.class, UpdateGroup.class})
	private Long price;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public Long getTid() {
		return tid;
	}

	public void setTid(Long tid) {
		this.tid = tid;
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

	public Long getPrice() {
		return price;
	}

	public void setPrice(Long price) {
		this.price = price;
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
