package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.dao.XryVideoDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * 视频表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryVideoService")
public class XryVideoServiceImpl extends ServiceImpl<XryVideoDao, XryVideoEntity> implements XryVideoService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String title = (String)params.get("title");
		Page<XryVideoEntity> page = this.selectPage( new Query<XryVideoEntity>(params).getPage(),
					new EntityWrapper<XryVideoEntity>().like(StringUtils.isNotBlank(title),"title", title)
				);
		return new PageUtils(page);
	}

	@Override
	public XryVideoEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
	public void save(XryVideoEntity xryVideoEntity) {
		baseMapper.insert(xryVideoEntity);
	}

	@Override
	public void update(XryVideoEntity xryVideoEntity) {
		baseMapper.updateById(xryVideoEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
		this.deleteBatchIds(Arrays.asList(ids));
	}
}
