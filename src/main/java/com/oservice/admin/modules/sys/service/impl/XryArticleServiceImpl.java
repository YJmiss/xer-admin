package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryArticleDao;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryArticleService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 文章表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryArticleService")
public class XryArticleServiceImpl extends ServiceImpl<XryArticleDao, XryArticleEntity> implements XryArticleService {

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String type = (String) params.get("type");
        String title = (String) params.get("title");
        String flag = (String) params.get("flag");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("type",type);
        map.put("title","%" + title + "%");
        map.put("flag",flag);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
		return new PageUtils(pageList);
	}
	
    @Override
	public XryArticleEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryArticleEntity xryArticleEntity) {
        xryArticleEntity.setCreated(new Date());
        baseMapper.insert(xryArticleEntity);
	}

	@Override
    public void update(XryArticleEntity xryArticleEntity) {
        xryArticleEntity.setCreated(new Date());
        baseMapper.updateById(xryArticleEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void updateArticleStatus(Map<String, Object> params) {
        baseMapper.updateArticleStatus(params);
    }

    @Override
    public void updateArticleRecommend(Map<String, Object> params) {
        baseMapper.updateArticleRecommend(params);
    }

}
