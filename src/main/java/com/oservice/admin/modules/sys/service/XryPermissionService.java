package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryPermissionEntity;
import com.oservice.admin.modules.sys.entity.XryRoleEntity;

import java.util.Map;

/**
 * 系统用户
 * app权限表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryPermissionService extends IService<XryPermissionEntity> {

	/**
	 * app权限分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据app权限id，查询app权限
	 * @param id
	 * @return
	 */
	XryPermissionEntity queryById(Long id);

	/**
	 * app权限保存
	 * @param xryPermissionEntity
	 */
	void save(XryPermissionEntity xryPermissionEntity);

	/**
	 * app权限修改
	 * @param xryPermissionEntity
	 */
	void update(XryPermissionEntity xryPermissionEntity);

	/**
	 * app权限删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

}
