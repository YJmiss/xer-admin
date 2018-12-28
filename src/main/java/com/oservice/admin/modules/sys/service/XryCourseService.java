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
	 * @param params
	 */
	void save(Map<String, Object> params);

	/**
	 * 课程修改
	 * @param params
	 */
	void update(Map<String, Object> params);

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
    Map<String, Object> queryCourseDetailByCourseId(Long courseId);

	/**
	 * 查询课程"目录"
	 * @param courseId
	 * @return
	 */
	Map<String, Object> listCourseCatalogByCourseId(Long courseId);

	/**
	 * 查询课程"评价"
	 * @param courseId
	 * @return
	 */
	Map<String, Object> listCourseCommentByCourseId(Long courseId,Integer pageNo, Integer pageSize);

	/**
	 * 查询"相关课程"
	 * @param courseId
	 * @return
	 */
	Map<String, Object> listRelatedCourseByCourseId(Long courseId);

	/**
	 * app端课程中心接口
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> appListCourseCenter(String params);

	/**
	 * 修改课程加入学习的计数
	 * @param courseId
	 * @param flag
	 */
    void updateCourseApplicationCount(Long courseId, Integer flag);

	/**
	 * 根据类目id查询课程数量
	 * @param id
	 * @return
	 */
	Integer countCourseByCatId(Long id);

	/**
	 * 根据类目id查询课程好评百分数
	 *
	 * @param courseId
	 * @return
	 */
	Integer getFeedback(Long courseId);

	/**
	 * 查询最近浏览课程列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> listCourseByUserIdAndCourseId(Map<String, Object> params);
}
