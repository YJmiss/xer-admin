package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XeyCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XeyCourseCatalogEntity;

import java.util.Map;

/**
 * 系统用户
 * 课程目录表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XeyCourserCatalogService extends IService<XeyCourseCatalogEntity> {

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
	XeyCourseCatalogEntity queryById(Long id);

	/**
	 * 课程目录保存
	 * @param xeyCourseCatalogEntity
	 */
	void save(XeyCourseCatalogEntity xeyCourseCatalogEntity);

	/**
	 * 课程目录修改
	 * @param xeyCourseCatalogEntity
	 */
	void update(XeyCourseCatalogEntity xeyCourseCatalogEntity);

	/**
	 * 课程目录删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

}
