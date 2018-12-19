package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryOrganizationService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
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
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String title = (String) params.get("title");
        String cid = (String) params.get("cid");
        String tid = (String) params.get("tid");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
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
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String catalogId = (String) params.get("catalogId");
        String courseId = (String) params.get("courseId");
        String examineStatus = (String) params.get("examineStatus");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("cid",catalogId);
        map.put("courseId",courseId);
        map.put("examineStatus",examineStatus);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.examineCountTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
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
    public Map<String, Object> queryCourseDetailByCourseId(Long courseId) {
        Map<String, Object> params = new HashMap<>();
        // 1、查询课程信息（课程标题、课程详情、课程价格）
        XryCourseEntity courseDetailContent = baseMapper.selectById(courseId);
        params.put("detailContent", courseDetailContent);
        // 2、根据课程id查询学习人数
        Integer courseStudentCount = baseMapper.countStudentByCourseId(courseId);
        params.put("courseStudentCount", courseStudentCount);
        // 3、根据课程id查询评价人数
        Integer courseCommentCount = baseMapper.countCommentByCourseId(courseId);
        params.put("courseCommentCount", courseCommentCount);
        // 4、根据课程id查询好评度
        double courseGoodPraiseCount = baseMapper.countGoodPraiseByCourseId(courseId);
        params.put("courseGoodPraiseCount", courseGoodPraiseCount);
        // 5、查询课程讲师信息
        String teacherId = courseDetailContent.getTid();
        XryTeacherEntity teacher = xryTeacherService.selectById(teacherId);
        params.put("teacher", teacher);
        // 5.1、查询该讲师的好评度
        double teacherGoodPraiseCount = baseMapper.countGoodPraiseByTeacherId(teacherId);
        params.put("teacherGoodPraiseCount", teacherGoodPraiseCount);
        // 5.2、该讲师的课程数
        Integer teacherCourseCount = baseMapper.countCourseByTeacherId(teacherId);
        params.put("teacherCourseCount", teacherCourseCount);
        // 5.3、该讲师的学生数（所有课程学生的总数）
        Integer teacherStudentCount = baseMapper.countStudentByTeacherId(teacherId);
        params.put("teacherStudentCount", teacherStudentCount);
        // 6、查询讲师所属机构
        if (null != teacher) {
            Long orgId = teacher.getOrgId();
            XryOrganizationEntity organization = xryOrganizationService.selectById(orgId);
            params.put("organization", organization);
            if (null != organization) {
                // 6.1、查询该机构的好评度
                // 6.2、该机构的课程数
                Integer orgCourseCount = baseMapper.countCourseByOrgId(orgId);
                params.put("orgCourseCount", orgCourseCount);
                // 6.3、该机构的学生数
                Integer orgStudentCount = baseMapper.countStudentByOrgId(orgId);
                params.put("orgStudentCount", orgStudentCount);
            }
        }
        return params;
    }

    @Override
    public Map<String, Object> listCourseCatalogByCourseId(Long courseId) {
        Map<String, Object> params = new HashMap<>();
        // 1、根据课程id查询出所有目录
        List<Map<String, Object>> courseCatalogList = baseMapper.listCourseCatalogByCourseId(courseId);
        // 2、根据目录id查询视频
        if (courseCatalogList.size() > 0) {
            for (Map<String, Object> courseCatalog : courseCatalogList) {
                Long catalogId = (Long) courseCatalog.get("id");
                List<Map<String, Object>> videoList = baseMapper.listVideoByCourseCatalogId(catalogId);
                courseCatalog.put("videoList", videoList);
            }
        }
        params.put("courseCatalogList", courseCatalogList);
        return params;
    }

    @Override
    public Map<String, Object> listCourseCommentByCourseId(Long courseId,Integer pageNo, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        // 根据课程id查询评价信息
        Map<String, Object> map = new HashMap<>();
        map.put("pageNo", (pageNo - 1) * pageSize);
        map.put("pageSize", pageSize);
        map.put("courseId", courseId);
        List<Map<String, Object>> courseCommentList = baseMapper.listCourseCommentByCourseId(map);
        params.put("courseCommentList", courseCommentList);
        return params;
    }

    @Override
    public Map<String, Object> listRelatedCourseByCourseId(Long courseId) {
        Map<String, Object> params = new HashMap<>();
        // 1、根据课程id查询出课程类目（课程中所属类目是子类目）
        XryCourseEntity courseEntity = baseMapper.selectById(courseId);
        // 查询出的类目id是子类目的id
        Long courseCatId = courseEntity.getCid();
        // 1.1、查询出该类目下管理员设置推荐的课程
        List<Map<String, Object>> relatedCourseList = new ArrayList<>();
        List<Map<String, Object>> recommendCourseList = baseMapper.listCourseCatBySubCatId(courseCatId);
        if (recommendCourseList.size() > 0) {
            for (Map<String, Object> rc : recommendCourseList) {
                // 2、查询课程章节
                Integer catalogCount = baseMapper.countCatalogByCourseCatId((Long) rc.get("cid"));
                rc.put("catalogCount", catalogCount);
                // 3、查询学习人数
                Integer studentCount = baseMapper.countStudentByCourseCatId((Long) rc.get("cid"));
                rc.put("studentCount", studentCount);
            }
            relatedCourseList.addAll(recommendCourseList);
        }
        // 1.2、如果该类目下的课程有管理员设置推荐的课程，则首先选择
        if (recommendCourseList.size() < 10) {
            Integer courseCount = 10 - recommendCourseList.size();
            Map<String, Object> map = new HashMap<>();
            map.put("courseCatId", courseCatId);
            map.put("courseCount", courseCount);
            List<Map<String, Object>> courseCountList = baseMapper.listCourseCatBySubCatIdAndCount(map);
            if (courseCountList.size() > 0) {
                for (Map<String, Object> cc : courseCountList) {
                    // 2、查询课程章节
                    Integer catalogCount = baseMapper.countCatalogByCourseCatId((Long) cc.get("cid"));
                    cc.put("catalogCount", catalogCount);
                    // 3、查询学习人数
                    Integer studentCount = baseMapper.countStudentByCourseCatId((Long) cc.get("cid"));
                    cc.put("studentCount", studentCount);
                }
                relatedCourseList.addAll(courseCountList);
            }
        }
        params.put("relatedCourseList",relatedCourseList);
        return params;
    }

    @Override
    public List<Map<String, Object>> appListCourseCenter(String tokenParams) {
        JSONObject json = new JSONObject(tokenParams);
        // 1、取出app传过来的参数
        Integer sort = json.getInt("sort");
        Integer catId = json.getInt("catId");
        Integer type = json.getInt("type");
        Integer pageNo = json.getInt("pageNo");
        Integer pageSize = json.getInt("pageSize");
        // 价格区间选择
        Long priceTagStart = 0L;Long priceTagEnd = 0L;
        Integer priceTag = json.getInt("priceTag");
        Long customPriceStart = 0L;Long customPriceEnd = 0L;
        if (0 == priceTag) {    // 没选中标签
            JSONObject customPrice = json.getJSONObject("customPrice");
            customPriceStart = customPrice.getLong("customPriceStart");
            customPriceEnd = customPrice.getLong("customPriceEnd");
        } else if (1 == priceTag){    // 免费
            priceTagStart = 0L; priceTagEnd = 0L;                
        } else if (2 == priceTag) {     // 0-50
            priceTagStart = 0L;priceTagEnd = 50L;
        } else if (3 == priceTag) {     // 50-100
            priceTagStart = 50L;priceTagEnd = 100L;
        } else if (4 == priceTag) {    // 100-500
            priceTagStart = 100L;priceTagEnd = 500L;
        } else if (5 == priceTag) {    // 500-1000
            priceTagStart = 500L;priceTagEnd = 1000L;
        } else if (6 == priceTag) {   //  1000以上
            priceTagStart = 1000L;priceTagEnd = 100000000L;
        }
        // 2、使用map传参数
        Map<String, Object> params = new HashMap<>();
        params.put("sort", sort);
        params.put("catId", catId);
        params.put("type", type);
        params.put("pageNo", pageNo);
        params.put("pageSize", pageSize);
        // 价格区间
        params.put("priceTag", priceTag);
        params.put("priceTagStart", priceTagStart);
        params.put("priceTagEnd", priceTagEnd);
        // 价格输入
        params.put("customPriceStart", customPriceStart);
        params.put("customPriceEnd", customPriceEnd);
        List<Map<String, Object>> courseList = baseMapper.appListCourseCenter(params);
        if (courseList.size() > 0) {
            for (Map<String, Object> map : courseList) {
                Long courseId = Long.valueOf(String.valueOf(map.get("id")));
                // 1、查询学习人数
                Integer courseStudentCount = baseMapper.countStudentByCourseId(courseId);
                map.put("courseStudentCount", courseStudentCount);
                // 2、查询课程章节
                Integer catalogCount = baseMapper.countCatalogByCourseId(courseId);
                map.put("catalogCount", catalogCount);
            }
        }
        return courseList;
    }

    @Override
    public void updateCourseApplicationCount(Long courseId, Integer flag) {
        XryCourseEntity course = new XryCourseEntity();
        Integer applicantCount = course.getApplicantCount();
        if (1 == flag) {   // 计数+1
            applicantCount = applicantCount + 1;
        } else {   // 计数-1
            applicantCount = applicantCount - 1;
        }
        course.setApplicantCount(applicantCount);
        baseMapper.updateById(course);
    }

}
