package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryContentDao;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.service.XryContentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryContentService")
public class XryContentServiceImpl extends ServiceImpl<XryContentDao, XryContentEntity> implements XryContentService {

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
		Page<Map<String, Object>> pageList = new Page<>();
		Map<String ,Object> map = new HashMap<>();
		String pageNo = (String) params.get("page");
		String pageSize = (String) params.get("limit");
		String category = (String)params.get("category");
		String title = (String)params.get("title");
		String courseId = (String) params.get("courseId");
		String status = (String) params.get("status");
		map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
		map.put("pageSize",pageSize);
		map.put("title","%"+title+"%");
		map.put("category",category);
		map.put("courseId",courseId);
		map.put("status",status);
		// 查询返回的数据总数page.totalCount
		Long total = baseMapper.countTotal(map);
		pageList.setTotal(total);
		// page.list 查询返回的数据list
		List<Map<String, Object>> courseList = baseMapper.pageList(map);
		pageList.setRecords(courseList);

		return new PageUtils(pageList);
	}

	@Override
	public XryContentEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryContentEntity xryContentEntity) {
        xryContentEntity.setCreated(new Date());
        xryContentEntity.setUpdated(new Date());
        baseMapper.insert(xryContentEntity);
	}

	@Override
    public void update(XryContentEntity xryContentEntity) {
        xryContentEntity.setCreated(new Date());
        xryContentEntity.setUpdated(new Date());
        baseMapper.updateById(xryContentEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

	@Override
	public void updateContentStatus(Map<String, Object> params) {
		baseMapper.updateContentStatus(params);
	}

	@Override
	public List<XryContentEntity> getContentsByCat(int cat) {
		return baseMapper.getContentsByCat(cat);
	}

}
