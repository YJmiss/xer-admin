package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.*;

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

	/**
	 * 审核系统->课程审核
	 * @param record
	 */
	void recordExamineInfo(XryRecordEntity record);

	/**
	 * 查询首页好评好课
	 *
	 * @return
	 */
	List<XryGoodCourseEntity> getGoodCourse();

	/**
	 * 获取索引库信息
	 *
	 * @return
	 */
	List<SearcherItem> findAllItems();

	/**
	 * 获取索引库信息通过ID
	 *
	 * @return
	 */
	SearcherItem findItemsById(Long id);

	/**
	 * 推荐课程、取消推荐课程
	 * @param params
	 */
	void updateCourseRecommend(Map<String, Object> params);

	/**
	 * 查询用户已经选择喜好的课程
	 * @return
	 */
	XryRecommendEntity listRecommendCourseCatByUserId(Map<String, Object> params);

	/**
	 * 用户喜好添加
	 * @param params
	 */
	void appInsertRecommend(Map<String, Object> params);

	/**
	 * 用户喜好修改
	 * @param params
	 */
	void appUpdateRecommend(Map<String, Object> params);

    /**
     * app端查询"课程详情"
     *
     * @param courseId
     */
    Map<String, Object> queryCourseDetailContentByCourseId(Long courseId);
}
