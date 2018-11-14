package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 用户视频表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_user_video")
public class XryUserVideoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 用户视频id
	 */
	private String id;

	/**
	 * 用户ID
	 */
	private String xryUserId;

	/**
	 * 视频ID
	 */
	private String xryVideoId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXryUserId() {
		return xryUserId;
	}

	public void setXryUserId(String xryUserId) {
		this.xryUserId = xryUserId;
	}

	public String getXryVideoId() {
		return xryVideoId;
	}

	public void setXryVideoId(String xryVideoId) {
		this.xryVideoId = xryVideoId;
	}
}
