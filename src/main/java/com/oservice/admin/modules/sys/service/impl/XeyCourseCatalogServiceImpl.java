package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XeyCourseCatalogDao;
import com.oservice.admin.modules.sys.entity.XeyCourseCatalogEntity;
import com.oservice.admin.modules.sys.service.XeyCourserCatalogService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * 课程目录表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xeyCourseCatalogService")
public class XeyCourseCatalogServiceImpl extends ServiceImpl<XeyCourseCatalogDao, XeyCourseCatalogEntity> implements XeyCourserCatalogService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<XeyCourseCatalogEntity> page = this.selectPage(new Query<XeyCourseCatalogEntity>(params).getPage(), new EntityWrapper<>());
		
		return new PageUtils(page);
	}

	@Override
	public XeyCourseCatalogEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XeyCourseCatalogEntity xeyCourseCatalogEntity) {
		xeyCourseCatalogEntity.setCreated(new Date());
		xeyCourseCatalogEntity.setUpdated(new Date());
		baseMapper.insert(xeyCourseCatalogEntity);
	}

	@Override
	public void update(XeyCourseCatalogEntity xeyCourseCatalogEntity) {
		xeyCourseCatalogEntity.setCreated(new Date());
		xeyCourseCatalogEntity.setUpdated(new Date());
		baseMapper.updateById(xeyCourseCatalogEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
   		baseMapper.deleteById(ids);
	}
}
