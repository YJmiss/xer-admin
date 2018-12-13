package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseTeacherUserDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryCourseTeacherUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程报名和讲师关注表接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseTeacherUserService")
public class XryCourseTeacherUserServiceImpl extends ServiceImpl<XryCourseTeacherUserDao, XryCourseTeacherUserEntity> implements XryCourseTeacherUserService {
    
    @Override
    public Integer saveCourse(Map<String, Object> params) {
        XryCourseTeacherUserEntity courseTeacherUser = new XryCourseTeacherUserEntity();
        courseTeacherUser.setType((Integer) params.get("type"));
        courseTeacherUser.setCourseId((Long) params.get("courseId"));
        courseTeacherUser.setUserId((String) params.get("userId"));
        courseTeacherUser.setCreated(new Date());
        return baseMapper.insert(courseTeacherUser);
    }

    @Override
    public Integer saveTeacher(Map<String, Object> params) {
        XryCourseTeacherUserEntity courseTeacherUser = new XryCourseTeacherUserEntity();
        courseTeacherUser.setType((Integer) params.get("type"));
        courseTeacherUser.setTeacherId((String) params.get("teacherId"));
        courseTeacherUser.setUserId((String) params.get("userId"));
        courseTeacherUser.setCreated(new Date());
        return baseMapper.insert(courseTeacherUser);
    }

    @Override
    public PageUtils appPageListCourseByUserId(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        List<Map<String, Object>> courseList = baseMapper.appPageListCourseByUserId(params);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public PageUtils appPageListTeacherByUserId(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        List<Map<String, Object>> teacherList = baseMapper.appPageListTeacherByUserId(params);
        pageList.setRecords(teacherList);
        return new PageUtils(pageList);
    }

    @Override
    public void appDelCourseById(Map<String, Object> params) {
        baseMapper.appDelCourseById(params);
    }

    @Override
    public void appDelTeacherById(Map<String, Object> params) {
        baseMapper.appDelTeacherById(params);
    }

}
