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
 * 课程表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_course")
@ApiModel(value = "课程对象")
public class XryCourseEntity implements Serializable {
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
	private Long cid;
	/**
	 * 所属讲师ID
	 */
	private String tid;
	/**
	 * 课程属性：1-收费，2-免费
	 */
	private Integer property;
	/**
	 * 课程状态，1-未审核，2-未通过，3-通过审核（通过审核未上架），4-通过审核已上架，5、已下架
	 */
	private Integer status;
	/**
	 * 课程价格 默认0，单位为：分
	 */
	private Long price;
	/**
	 * 是否推荐该课程
	 */
	private Integer recommend;
	/**
	 * 课程人气数（不可删减，只可增加）
	 */
	private Integer applicantCount;

	/**
	 * 实时报名人数（可删减）
	 */
	private Integer applicantSum;

	/**
	 * app端收藏里判断用户拉出、收起
	 */
	private Integer appStatus;

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

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
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

	public Integer getApplicantCount() {
		return applicantCount;
	}

	public void setApplicantCount(Integer applicantCount) {
		this.applicantCount = applicantCount;
	}

	public Integer getApplicantSum() {
		return applicantSum;
	}

	public void setApplicantSum(Integer applicantSum) {
		this.applicantSum = applicantSum;
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

	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	public Integer getAppStatus() {
		return appStatus;
	}

	public void setAppStatus(Integer appStatus) {
		this.appStatus = appStatus;
	}
}
