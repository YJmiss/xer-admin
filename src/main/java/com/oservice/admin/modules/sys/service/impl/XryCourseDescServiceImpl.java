package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseDescDao;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.service.XryCourseDescService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 课程描述表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseDescService")
public class XryCourseDescServiceImpl extends ServiceImpl<XryCourseDescDao, XryCourseDescEntity> implements XryCourseDescService {


	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		// 所属课程的搜索
		String courseId =  (String) params.get("courseId");
		Page<Map<String, Object>> pageList = new Page<>();
		Map<String ,Object> map = new HashMap<>();
		String page = (String) params.get("page");
		String limit = (String) params.get("limit");
		map.put("page",page);
		map.put("limit",limit);
		map.put("courseId",courseId);
		List<Map<String, Object>> courseList = baseMapper.pageList(map);
		pageList.setRecords(courseList);
		return new PageUtils(pageList);
	}

	@Override
	public XryCourseDescEntity queryById(Long id) {
		return baseMapper.queryById(id);
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
        baseMapper.deleteBatchIds(Arrays.asList(ids));
	}
}
