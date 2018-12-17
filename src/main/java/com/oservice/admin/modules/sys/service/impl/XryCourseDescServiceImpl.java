package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseDescDao;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.service.XryCourseDescService;
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
		Page<Map<String, Object>> pageList = new Page<>();
		Map<String ,Object> map = new HashMap<>();
		String pageNo = (String) params.get("page");
		String pageSize = (String) params.get("limit");
		String courseId =  (String) params.get("courseId");
		map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
		map.put("pageSize",pageSize);
		map.put("courseId",courseId);
		// 查询返回的数据总数page.totalCount
		Long total = baseMapper.countTotal(map);
		pageList.setTotal(total);
		// page.list 查询返回的数据list
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
