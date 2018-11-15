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
	


}
