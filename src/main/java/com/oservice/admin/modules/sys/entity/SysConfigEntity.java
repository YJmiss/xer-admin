package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import io.swagger.annotations.ApiModel;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 *
 * @author LingDu
 * @version 1.0
 */
@TableName("sys_config")
@ApiModel(value = "系统配置信息对象")
public class SysConfigEntity {
	@TableId
	private Long id;

	@NotBlank(message="参数名不能为空")
	private String paramKey;

	@NotBlank(message="参数值不能为空")
	private String paramValue;
	private String remark;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getParamKey() {
		return paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
