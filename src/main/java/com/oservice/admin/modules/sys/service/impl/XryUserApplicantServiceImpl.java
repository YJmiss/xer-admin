package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryUserApplicantDao;
import com.oservice.admin.modules.sys.entity.XryUserApplicantEntity;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;
import com.oservice.admin.modules.sys.service.XryUserApplicantService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 机构表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryUserApplicantService")
public class XryUserApplicantServiceImpl extends ServiceImpl<XryUserApplicantDao, XryUserApplicantEntity> implements XryUserApplicantService {


    @Override
    public Integer appSaveCourse(Map<String, Object> params) {
        XryUserApplicantEntity userApplicant = new XryUserApplicantEntity();
        userApplicant.setObjId((Long) params.get("objId"));
        userApplicant.setUserId((String) params.get("userId"));
        userApplicant.setObjType((Integer) params.get("objType"));
        userApplicant.setObjStatus(0);
        userApplicant.setCreateTime(new Date());
        return baseMapper.insert(userApplicant);
    }

    @Override
    public PageUtils appPageListCourseByUserId(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        List<Map<String, Object>> courseList = baseMapper.appPageListCourseByUserId(params);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public void appDelCourseById(Map<String, Object> params) {
        baseMapper.appDelCourseById(params);
    }

    @Override
    public List<String> listUserIdByCourseId(Long courseId) {
        List<XryUserApplicantEntity> courseList = baseMapper.listUserIdByCourseId(courseId);
        List<String> userIds = null;
        if (courseList.size() > 0) {
            userIds = new ArrayList<>();
            for (XryUserApplicantEntity course : courseList) {
                String userId = course.getUserId();
                userIds.add(userId);
            }
        }
        return userIds;
    }

    @Override
    public List<XryUserApplicantEntity> countApplicantByCourseId(Long courseDetailId) {
        return baseMapper.countApplicantByCourseId(courseDetailId);
    }

    @Override
    public XryUserApplicantEntity isApplicantByUserIdAndCourseId(Long courseId, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId" ,userId);
        params.put("courseId" ,courseId);
        return baseMapper.isApplicantByUserIdAndCourseId(params);
    }

    @Override
    public void removeUserCourseByUserId(String userId) {
        baseMapper.removeUserCourseByUserId(userId);
    }

}
