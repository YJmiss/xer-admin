package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryOrganizationEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;

import java.util.Map;

/**
 * 系统用户
 * 讲师表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryTeacherService extends IService<XryTeacherEntity> {

	/**
	 * 讲师分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据讲师id查询讲师
	 * @param id
	 * @return
	 */
	XryTeacherEntity queryById(Long id);

	/**
	 * 讲师保存
	 * @param xryTeacherEntity
	 */
	void save(XryTeacherEntity xryTeacherEntity);

	/**
	 * 讲师删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 讲师认证
	 * @param record
	 */
	void recordExamineInfo(XryRecordEntity record);


}
