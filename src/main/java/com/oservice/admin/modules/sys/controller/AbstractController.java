package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.modules.sys.entity.SysUserEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller公共组件
 *
 * @author YJmiss
 * @version 1.0
 */
public abstract class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected XryUserEntity getAppUser() {
		return (XryUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected String getAppUserId() {
		return getAppUser().getId();
	}
	protected Long getUserId() {
		return getUser().getUserId();
	}
}
