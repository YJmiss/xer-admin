package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryOrganizationEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 机构表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryOrganizationService extends IService<XryOrganizationEntity> {

	/**
	 * 机构分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据机构id查询机构
	 * @param id
	 * @return
	 */
    Map<String, Object> queryById(Long id);

	/**
	 * 机构保存
     * @param params
	 */
    void save(String[] params);

	/**
	 * 机构删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 机构上、下架
	 * @param record
	 */
	void recordExamineInfo(XryRecordEntity record);

    /**
     * app查询机构列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> listByUserId(Map<String, Object> params);
}
