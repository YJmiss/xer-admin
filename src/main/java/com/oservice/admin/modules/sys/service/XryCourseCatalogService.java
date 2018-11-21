package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程目录表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryCourseCatalogService extends IService<XryCourseCatalogEntity> {

	/**
     * 课程目录分页查询
	 * @param params
     * @return
     */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据课程目录id，查询课程目录
	 * @param id
	 * @return
	 */
	XryCourseCatalogEntity queryById(Long id);

	/**
	 * 课程目录保存
	 * @param xryCourseCatalogEntity
	 */
	void save(XryCourseCatalogEntity xryCourseCatalogEntity);

	/**
	 * 课程目录修改
	 * @param xryCourseCatalogEntity
	 */
	void update(XryCourseCatalogEntity xryCourseCatalogEntity);

	/**
	 * 课程目录删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 构造目录树
	 *
	 * @return
	 */
	List<XryCourseCatalogEntity> treeCourseCatalog();


	/**
	 * 目录上、下架
	 * @param params
	 */
	void updateCourseCatalogStatus(Map<String, Object> params);
}
