package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 线下支付表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryPayOfflineService extends IService<XryPayOfflineEntity> {

	/**
	 * 线下支付分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据线下支付id，查询线下支付
	 * @param id
	 * @return
	 */
	XryPayOfflineEntity queryById(Long id);

	/**
	 * 线下支付保存
	 * @param xryPayOffline
	 */
	void save(XryPayOfflineEntity xryPayOffline);

	/**
	 * 线下支付修改
	 * @param xryPayOfflineEntity
	 */
	void update(XryPayOfflineEntity xryPayOfflineEntity);

	/**
	 * 线下支付删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);
	
}
