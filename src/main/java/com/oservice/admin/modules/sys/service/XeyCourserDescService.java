package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XeyCourseDescEntity;
import java.util.Map;

/**
 * 系统用户
 * 课程描述表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XeyCourserDescService extends IService<XeyCourseDescEntity> {

	/**
     * 课程分页查询
	 * @param params
     * @return
     */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据课程id，查询课程
	 * @param id
	 * @return
	 */
	XeyCourseDescEntity queryById(Long id);

	/**
	 * 课程描述保存
	 * @param xeyCourseDescEntity
	 */
	void save(XeyCourseDescEntity xeyCourseDescEntity);

	/**
	 * 课程描述修改
	 * @param xeyCourseDescEntity
	 */
	void update(XeyCourseDescEntity xeyCourseDescEntity);

	/**
	 * 课程描述删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

}
