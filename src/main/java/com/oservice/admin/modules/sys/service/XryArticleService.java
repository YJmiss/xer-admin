package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 文章表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryArticleService extends IService<XryArticleEntity> {

	/**
	 * 文章分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据文章id，查询文章
	 * @param id
	 * @return
	 */
	XryArticleEntity queryById(Long id);

	/**
	 * 文章保存
	 * @param xryArticleEntity
	 */
	void save(XryArticleEntity xryArticleEntity);

	/**
	 * 文章修改
	 * @param xryArticleEntity
	 */
	void update(XryArticleEntity xryArticleEntity);

	/**
	 * 文章删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 发布文章、取消发布文章
	 * @param params
	 */
	void updateArticleStatus(Map<String,Object> params);

}
