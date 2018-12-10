package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseService")
public class XryCourseServiceImpl extends ServiceImpl<XryCourseDao, XryCourseEntity> implements XryCourseService {

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        String cid = (String) params.get("cid");
        String tid = (String) params.get("tid");
        // 重写分页查询 page limit title cid
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        map.put("page",page);
        map.put("limit",limit);
        map.put("title","%"+title+"%");
        map.put("cid",cid);
        map.put("tid",tid);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
		return new PageUtils(pageList);
	}

    @Override
    public PageUtils examineList(Map<String, Object> params) {
        String catalogId = (String) params.get("catalogId");
        String courseId = (String) params.get("courseId");
        String examineStatus = (String) params.get("examineStatus");
        // 重写分页查询 page limit title cid
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        map.put("page",page);
        map.put("limit",limit);
        map.put("cid",catalogId);
        map.put("courseId",courseId);
        map.put("examineStatus",examineStatus);
        List<Map<String, Object>> courseList = baseMapper.examineList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
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

    @Override
    public XryCourseCatalogEntity queryCourseCatalogByCourseId(Long id) {
        return baseMapper.queryCourseCatalogByCourseId(id);
    }

    @Override
    public XryCourseDescEntity queryCourseDescById(Long id) {
        return baseMapper.queryCourseDescById(id);
    }

    @Override
    public void updateCourseStatus(Map<String,Object> params) {
        baseMapper.updateCourseStatus(params);
    }

    @Override
    public void recordExamineInfo(XryRecordEntity record) {
        Map<String, Object> params = new HashMap<>();
        Long id = record.getRecordId();
        Integer action = record.getActionNumber();
        params.put("id",id);
        params.put("status",action);
        baseMapper.recordExamineInfo(params);

    }

    @Override
    public List<XryGoodCourseEntity> getGoodCourse() {
        return baseMapper.getGoodCourse();
    }

    @Override
    public List<SearcherItem> findAllItems() {
        return baseMapper.findAllItems();
    }

    @Override
    public SearcherItem findItemsById(Long id) {
        return baseMapper.findItemsById(id);
    }

    @Override
    public void updateCourseRecommend(Map<String, Object> params) {
        baseMapper.updateCourseRecommend(params);
    }

    @Override
    public XryRecommendEntity listRecommendCourseCatByUserId(Map<String, Object> params) {
        return baseMapper.listRecommendCourseCatByUserId(params);
    }

    @Override
    public void appInsertRecommend(Map<String, Object> params) {
        baseMapper.appInsertRecommend(params);
    }

    @Override
    public void appUpdateRecommend(Map<String, Object> params) {
        baseMapper.appUpdateRecommend(params);
    }

}
