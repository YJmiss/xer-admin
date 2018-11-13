package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseCatalogDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		Page<XryCourseCatalogEntity> page = this.selectPage(new Query<XryCourseCatalogEntity>(params).getPage(), new EntityWrapper<>());
		
		return new PageUtils(page);
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
	public List<XryCourseCatalogEntity> treeCourseList() {
		return  baseMapper.treeCourseList();
	}

	@Override
	public List<XryCourseCatalogEntity> treeCourseCatalogList() {
		return baseMapper.treeCourseCatalogList();
	}
}
