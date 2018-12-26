package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 课程类目表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryCourseCatService extends IService<XryCourseCatEntity> {

	/**
	 * 课程类目分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据课程类目类目id，查询课程类目
	 * @param id
	 * @return
	 */
	XryCourseCatEntity queryById(Long id);

	/**
	 * 课程类目保存
	 * @param xryCourseCatEntity
	 */
	void save(XryCourseCatEntity xryCourseCatEntity);

	/**
	 * 课程类目修改
	 * @param xryCourseCatEntity
	 */
	void update(XryCourseCatEntity xryCourseCatEntity);

	/**
	 * 课程类目删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 构造课程类目树
	 *
	 * @return
	 */
	List<XryCourseCatEntity> treeCourseCat(Integer flag);

	/**
	 * 查询课程信息
	 * @param id
	 * @return
	 */
	List<XryCourseEntity> listCourseByCourseCatalogId(Long id);

	/**
	 * 查询类目信息
	 * @param id
	 * @return
	 */
	List<XryCourseCatEntity> isParentCourseCatalogById(Long id);

	/**
	 * 查询所有的课程类目
	 * @return
	 */
	List<Map<String, Object>> listCourseCat();

	/**
	 * 修改类目状态
	 * @param params
	 */
    void updateCourseCatStatusByCatId(Map<String, Object> params);

	/**
	 * 是父类目，查询出所有子类目
	 * @param courseCatId
	 * @return
	 */
	String[] listCourseCatByParentCatId(Long courseCatId);
}
