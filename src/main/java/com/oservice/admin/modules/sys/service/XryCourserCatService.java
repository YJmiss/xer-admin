package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import java.util.Map;

/**
 * 系统用户
 * 课程类目表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryCourserCatService extends IService<XryCourseCatEntity> {

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
	 * @param xeyCourseCatEntity
	 */
	void save(XryCourseCatEntity xeyCourseCatEntity);

	/**
	 * 课程类目修改
	 * @param xeyCourseCatEntity
	 */
	void update(XryCourseCatEntity xeyCourseCatEntity);

	/**
	 * 课程类目删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

}
