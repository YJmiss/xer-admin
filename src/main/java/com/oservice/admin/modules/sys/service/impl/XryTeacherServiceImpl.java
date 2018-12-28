package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.DateUtils;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseCatDao;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.dao.XryTeacherDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统用户
 * 讲师表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryTeacherService")
public class XryTeacherServiceImpl extends ServiceImpl<XryTeacherDao, XryTeacherEntity> implements XryTeacherService {
    @Resource
    private XryCourseDao xryCourseDao;
    @Resource
    private XryCourseCatDao xryCourseCatDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String userPhone = (String) params.get("userPhone");
        String realName = (String) params.get("realName");
        String recommend = (String) params.get("recommend");
        String flag = (String) params.get("status");
        String created = (String) params.get("created");
        String flag2 = (String) params.get("teacherListStatus");
        map.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize", pageSize);
        if (null != realName && "".equals(realName)) {
            map.put("realName", "%" + realName + "%");
        }
        map.put("recommend", recommend);
        if (null != flag && "".equals(flag)) {
            Integer status = Integer.valueOf(flag);
            map.put("status", status);
        }
        // 讲师列表
        if (null != flag2 && "".equals(flag2)) {
            Integer status = Integer.valueOf(flag2);
            map.put("status", status);
        }
        if (null != userPhone && "".equals(userPhone)) {
            map.put("userPhone", "%" + userPhone + "%");
        }
        if (null != created && !"".equals(created)) {
            String createTime[] = created.split("T")[0].split("-");
            Integer timeYear = Integer.valueOf(createTime[0]);
            Integer timeMonth = Integer.valueOf(createTime[1]);
            Integer timeDay = Integer.valueOf(createTime[2]) + 1;
            created = timeYear + "-" +timeMonth + "-" + timeDay;
            System.out.println("created接收到日期为：" + created);
            map.put("created", "%" + created + "%");
        }
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> teacherList = baseMapper.pageList(map);
        pageList.setRecords(teacherList);
        return new PageUtils(pageList);
    }

    @Override
    public Map<String, Object> queryById(Long id) {
        return baseMapper.queryById(id);
    }

    @Override
    public void save(String[] params) {
        XryTeacherEntity xryTeacherEntity = new XryTeacherEntity();
        xryTeacherEntity.setUserId(params[1]);
        xryTeacherEntity.setRealName(params[2]);
        xryTeacherEntity.setIdCard(params[3]);
        xryTeacherEntity.setIdCardFront(params[4]);
        xryTeacherEntity.setIdCardBack(params[5]);
        // type:1：个人认证  2：机构认证
        xryTeacherEntity.setType(Integer.valueOf(params[6]));
        xryTeacherEntity.setOrgId(Long.valueOf(params[7]));
        xryTeacherEntity.setStatus(Integer.valueOf(params[8]));
        xryTeacherEntity.setCreated(new Date());

        String teacherId = params[0];
        if (StringUtils.isNotBlank(teacherId)) {
            // 修改保存
            xryTeacherEntity.setId(String.valueOf(teacherId));
            baseMapper.updateById(xryTeacherEntity);
        } else {
            // 添加保存
            baseMapper.insert(xryTeacherEntity);
        }
    }

    @Override
    public void deleteBatch(String[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }


    @Override
    public void recordExamineInfo(XryRecordEntity record) {
        Map<String, Object> params = new HashMap<>();
        Long id = record.getRecordId();
        Integer action = record.getActionNumber();
        params.put("id", id);
        params.put("status", action);
        baseMapper.recordExamineInfo(params);
    }

    @Override
    public List<Map<String, Object>> appPageListTeacherByUserId(Map<String, Object> params) {
        // 1、查询讲师信息
        List<Map<String, Object>> attentionTeacherList = baseMapper.appPageListTeacherByUserId(params);
        if (attentionTeacherList.size() > 0) {
            for (Map<String, Object> attentionTeacher : attentionTeacherList) {
                String teacherId = String.valueOf(attentionTeacher.get("teacherId"));
                // 1、根据讲师id查询出讲师所上的课程
                List<Map<String, Object>> courseCatList = baseMapper.listCourseByTeacherId(teacherId);
                if (courseCatList.size() > 0) {
                    for (Map<String, Object> map : courseCatList) {
                        Long subCourseCatId = Long.valueOf(String.valueOf(map.get("cid")));
                        // 根据子类目的id查询出父类目的信息（父类目的id）
                        XryCourseCatEntity subCourseCat = xryCourseCatDao.selectById(subCourseCatId);
                        Long parentCatId = subCourseCat.getParentId();
                        XryCourseCatEntity parentCourseCat = xryCourseCatDao.selectById(parentCatId);
                        attentionTeacher.put("parentCourseCatName", parentCourseCat.getName());
                    }
                }
                // 3、查询讲师的课程数
                Integer teacherCourseCount = baseMapper.countCourseByTeacherId(teacherId);
                attentionTeacher.put("teacherCourseCount", teacherCourseCount);
                // 4、查询讲师的学生数
                Integer teacherStudentCount = baseMapper.countStudentByTeacherId(teacherId);
                attentionTeacher.put("teacherStudentCount", teacherStudentCount);
            }
        }
        return attentionTeacherList;
    }

    @Override
    public List<XryTeacherEntity> treeTeacher() {
        return baseMapper.treeTeacher();
    }

    @Override
    public void updateTeacherRecommend(Map<String, Object> params) {
        baseMapper.updateTeacherRecommend(params);
    }

    @Override
    public void updateTeacherAttention(String teacherId, Integer flag) {
        XryTeacherEntity teacher = baseMapper.selectById(teacherId);
        // 人气数
        Integer attentionCount = teacher.getAttentionCount();
        // 实时报名人数
        Integer attentionSum = teacher.getAttentionSum();
        if (1 == flag) {
            attentionSum = attentionSum + 1;
            attentionCount = attentionCount + 1;
        } else {
            attentionSum = attentionSum - 1;
        }
        teacher.setAttentionCount(attentionCount);
        teacher.setAttentionSum(attentionSum);
        baseMapper.updateById(teacher);
    }

    @Override
    public List<Map<String, Object>> appListStarTeacherByUserId(Map<String, Object> params) {
        return baseMapper.appListStarTeacherByUserId(params);
    }

    @Override
    public Map<String, Object> appQueryTeacherDetailByTeacherId(String teacherId) {
        return baseMapper.appQueryTeacherDetailByTeacherId(teacherId);
    }

    @Override
    public List<Map<String, Object>> listTeacherCourseByTeacherId(String teacherId) {
        List<Map<String, Object>> teacherCourseList = baseMapper.listTeacherCourseByTeacherId(teacherId);
        if (teacherCourseList.size() > 0) {
            for (Map<String, Object> map : teacherCourseList) {
                Long courseId = Long.valueOf(String.valueOf(map.get("id")));
                // 根据课程id查询该课程的学生人数
                Integer courseStudentCount = baseMapper.countStudentByCourseId(courseId);
                map.put("courseStudentCount", courseStudentCount);
            }
        }
        return teacherCourseList;
    }

    @Override
    public Integer countUserApplicantByUserId(String userId) {
        return baseMapper.countUserApplicantByUserId(userId);
    }


}
