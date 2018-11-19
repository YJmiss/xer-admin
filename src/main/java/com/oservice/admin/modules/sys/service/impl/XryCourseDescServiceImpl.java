package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseDescDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.service.XryCourseDescService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * 课程描述表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourserDescService")
public class XryCourseDescServiceImpl extends ServiceImpl<XryCourseDescDao, XryCourseDescEntity> implements XryCourseDescService {


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		// 所属课程的搜索
		String courseid =  (String) params.get("courseid");
		Page<XryCourseDescEntity> page = this.selectPage(new Query<XryCourseDescEntity>(params).getPage(),
				new EntityWrapper<XryCourseDescEntity>().like(StringUtils.isNotBlank(courseid), "courseid",courseid)
		);
		return new PageUtils(page);
	}

	@Override
	public XryCourseDescEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryCourseDescEntity xryCourseDescEntity) {
        xryCourseDescEntity.setCreated(new Date());
        xryCourseDescEntity.setUpdated(new Date());
        baseMapper.insert(xryCourseDescEntity);
	}

	@Override
    public void update(XryCourseDescEntity xryCourseDescEntity) {
        xryCourseDescEntity.setCreated(new Date());
        xryCourseDescEntity.setUpdated(new Date());
        baseMapper.updateById(xryCourseDescEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
	}
}
