package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryCourseService extends IService<XryCourseEntity> {

	/**
	 * 课程分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 查询课程审核列表
	 * @param params
	 * @return
	 */
	PageUtils examineList(Map<String, Object> params);

	/**
	 * 根据课程id，查询课程
	 * @param id
	 * @return
	 */
	XryCourseEntity queryById(Long id);

	/**
	 * 课程保存
	 * @param xryCourseEntity
	 */
	void save(XryCourseEntity xryCourseEntity);

	/**
	 * 课程修改
	 * @param xryCourseEntity
	 */
	void update(XryCourseEntity xryCourseEntity);

	/**
	 * 课程删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 构造课程树
	 *
	 * @return
	 */
	List<XryCourseEntity> treeCourse();

	/**
	 * 查询课程目录
	 *
	 * @return
	 */
	XryCourseCatalogEntity queryCourseCatalogByCourseId(Long id);

	/**
	 * 查询课程描述
	 *
	 * @return
	 */
	XryCourseDescEntity queryCourseDescById(Long id);

	/**
	 * 课程上、下架
	 * @param params
	 */
	void updateCourseStatus(Map<String, Object> params);
}
