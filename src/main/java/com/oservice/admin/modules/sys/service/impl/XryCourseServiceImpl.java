package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourserService")
public class XryCourseServiceImpl extends ServiceImpl<XryCourseDao, XryCourseEntity> implements XryCourseService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<XryCourseEntity> page = this.selectPage(new Query<XryCourseEntity>(params).getPage(), new EntityWrapper<>());

		return new PageUtils(page);
	}

	@Override
	public XryCourseEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XryCourseEntity xryCourseEntity) {
		xryCourseEntity.setCreated(new Date());
		xryCourseEntity.setUpdated(new Date());
		baseMapper.insert(xryCourseEntity);
	}

	@Override
	public void update(XryCourseEntity xryCourseEntity) {
		xryCourseEntity.setCreated(new Date());
		xryCourseEntity.setUpdated(new Date());
		baseMapper.updateById(xryCourseEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		this.deleteBatchIds(Arrays.asList(ids));
	}

	@Override
	public List<XryCourseEntity> treeCourse() {
		return baseMapper.treeCourse();
	}

}
