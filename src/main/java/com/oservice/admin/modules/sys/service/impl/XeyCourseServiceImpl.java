package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XeyCourseDao;
import com.oservice.admin.modules.sys.entity.XeyCourseEntity;
import com.oservice.admin.modules.sys.service.XeyCourserService;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xeyCourserService")
public class XeyCourseServiceImpl extends ServiceImpl<XeyCourseDao, XeyCourseEntity> implements XeyCourserService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<XeyCourseEntity> page = this.selectPage(new Query<XeyCourseEntity>(params).getPage(), new EntityWrapper<>());

		return new PageUtils(page);
	}

	@Override
	public XeyCourseEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XeyCourseEntity xeyCourseEntity) {
		xeyCourseEntity.setCreated(new Date());
		xeyCourseEntity.setUpdated(new Date());
		baseMapper.insert(xeyCourseEntity);
	}

	@Override
	public void update(XeyCourseEntity xeyCourseEntity) {
		xeyCourseEntity.setCreated(new Date());
		xeyCourseEntity.setUpdated(new Date());
		baseMapper.updateById(xeyCourseEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		baseMapper.deleteById(ids);
	}
}
