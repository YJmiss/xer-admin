package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseCatalogDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 课程目录表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseCatalogService")
public class XryCourseCatalogServiceImpl extends ServiceImpl<XryCourseCatalogDao, XryCourseCatalogEntity> implements XryCourseCatalogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String title = (String) params.get("title");
		// 所属课程的搜索
		String courseid =  (String) params.get("courseid");
		Page<Map<String, Object>> pageList = new Page<>();
		Map<String ,Object> map = new HashMap<>();
		String page = (String) params.get("page");
		String limit = (String) params.get("limit");
		map.put("page",page);
		map.put("limit",limit);
		map.put("title","%"+title+"%");
		map.put("courseid",courseid);
		List<Map<String, Object>> courseList = baseMapper.pageList(map);
		pageList.setRecords(courseList);
		return new PageUtils(pageList);
	}

	@Override
	public XryCourseCatalogEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XryCourseCatalogEntity xryCourseCatalogEntity) {
		xryCourseCatalogEntity.setCreated(new Date());
		xryCourseCatalogEntity.setUpdated(new Date());
		baseMapper.insert(xryCourseCatalogEntity);
	}

	@Override
	public void update(XryCourseCatalogEntity xryCourseCatalogEntity) {
		xryCourseCatalogEntity.setCreated(new Date());
		xryCourseCatalogEntity.setUpdated(new Date());
		baseMapper.updateById(xryCourseCatalogEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public List<XryCourseCatalogEntity> treeCourseCatalog(Long courseId) {
		return baseMapper.treeCourseCatalog(courseId);
	}

	@Override
	public void updateCourseCatalogStatus(Map<String, Object> params) {
		 baseMapper.updateCourseCatalogStatus(params);
	}

}
