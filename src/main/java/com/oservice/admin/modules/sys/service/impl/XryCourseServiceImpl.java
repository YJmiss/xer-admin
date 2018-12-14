package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryOrganizationService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseService")
public class XryCourseServiceImpl extends ServiceImpl<XryCourseDao, XryCourseEntity> implements XryCourseService {
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private XryOrganizationService xryOrganizationService;

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        String title = (String) params.get("title");
        String cid = (String) params.get("cid");
        String tid = (String) params.get("tid");
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

    @Override
    public Map<String, Object> queryCourseDetailContentByCourseId(Long courseId) {
        Map<String, Object> params = new HashMap<>();
        // 1、查询课程信息（课程标题、课程详情、课程价格）
        XryCourseEntity courseDetailContent = baseMapper.selectById(courseId);
        params.put("detailContent",courseDetailContent);
        // 2、根据课程id查询学习人数
        Integer courseStudentCount = baseMapper.countStudentByCourseId(courseId);
        params.put("studentCount",courseStudentCount);
        // 3、根据课程id查询评价人数
        Integer commentCount = baseMapper.countCommentByCourseId(courseId);
        params.put("commentCount",commentCount);
        // 4、根据课程id查询好评度
        List<Integer> courseGoodPraiseSum = baseMapper.countGoodPraiseByCourseId(courseId);
        Integer courseCount = 0;
        for (Integer c : courseGoodPraiseSum) { courseCount += c; }
        double courseGoodPraiseCount = (courseCount / courseGoodPraiseSum.size()) / 10;
        params.put("courseGoodPraiseCount",courseGoodPraiseCount);
        // 5、查询课程讲师信息
        String teacherId = courseDetailContent.getTid();
        XryTeacherEntity teacher = xryTeacherService.selectById(teacherId);
        params.put("teacher",teacher);
        // 5.1、查询该讲师的好评度
        List<Integer> teacherGoodPraiseSum = baseMapper.countGoodPraiseByTeacherId(teacherId);
        Integer teacherCount = 0;
        for (Integer t : courseGoodPraiseSum) { teacherCount += t; }
        double teacherGoodPraiseCount = (teacherCount / teacherGoodPraiseSum.size()) / 10;
        params.put("teacherGoodPraiseCount",teacherGoodPraiseCount);
        // 5.2、该讲师的课程数
        Integer teacherCourseCount = baseMapper.countCourseByTeacherId(teacherId);
        params.put("teacherCourseCount",teacherCourseCount);
        // 5.3、该讲师的学生数（所有课程学生的总数）
        Integer studentCourseCount = baseMapper.countStudentByTeacherId(teacherId);
        params.put("studentCourseCount",studentCourseCount);
        // 6、查询讲师所属机构
        if (null != teacher) {
            Long orgId = teacher.getOrgId();
            XryOrganizationEntity organization = xryOrganizationService.selectById(orgId);
            params.put("organization",organization);
            if (null != organization) {
                // 6.1、查询该机构的好评度
                // 6.2、该机构的课程数
                Integer orgCourseCount = baseMapper.countCourseByOrgId(orgId);
                params.put("orgCourseCount",orgCourseCount);
                // 6.3、该机构的学生数
                Integer orgStudentCount = baseMapper.countStudentByOrgId(orgId);
                params.put("orgStudentCount",orgStudentCount);
            }
        }
        return params;
    }

}
