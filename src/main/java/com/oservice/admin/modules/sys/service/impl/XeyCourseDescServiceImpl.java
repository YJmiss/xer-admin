package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XeyCourseDao;
import com.oservice.admin.modules.sys.dao.XeyCourseDescDao;
import com.oservice.admin.modules.sys.entity.XeyCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XeyCourseEntity;
import com.oservice.admin.modules.sys.service.XeyCourserDescService;
import com.oservice.admin.modules.sys.service.XeyCourserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * 课程描述表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xeyCourserDescService")
public class XeyCourseDescServiceImpl extends ServiceImpl<XeyCourseDescDao, XeyCourseDescEntity> implements XeyCourserDescService {


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<XeyCourseDescEntity> page = this.selectPage(new Query<XeyCourseDescEntity>(params).getPage(), new EntityWrapper<>());

		return new PageUtils(page);
	}

	@Override
	public XeyCourseDescEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XeyCourseDescEntity xeyCourseDescEntity) {
		xeyCourseDescEntity.setCreated(new Date());
		xeyCourseDescEntity.setUpdated(new Date());
		baseMapper.insert(xeyCourseDescEntity);
	}

	@Override
	public void update(XeyCourseDescEntity xeyCourseDescEntity) {
		xeyCourseDescEntity.setCreated(new Date());
		xeyCourseDescEntity.setUpdated(new Date());
		baseMapper.updateById(xeyCourseDescEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		baseMapper.deleteById(ids);
	}
}
