package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseCatDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.service.XryCourserCatService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * 课程类目表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xeyCourseCatService")
public class XryCourseCatServiceImpl extends ServiceImpl<XryCourseCatDao, XryCourseCatEntity> implements XryCourserCatService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<XryCourseCatEntity> page = this.selectPage(new Query<XryCourseCatEntity>(params).getPage(), new EntityWrapper<>());
		
		return new PageUtils(page);
	}

	@Override
	public XryCourseCatEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XryCourseCatEntity xeyCourseCatEntity) {
		xeyCourseCatEntity.setCreated(new Date());
		xeyCourseCatEntity.setUpdated(new Date());
		baseMapper.insert(xeyCourseCatEntity);
	}

	@Override
	public void update(XryCourseCatEntity xeyCourseCatEntity) {
		xeyCourseCatEntity.setCreated(new Date());
		xeyCourseCatEntity.setUpdated(new Date());
		baseMapper.updateById(xeyCourseCatEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
   		baseMapper.deleteById(ids);
	}
}
