package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程报名和讲师关注表接口
 * @author LingDu
 * @version 1.0
 */
public interface XryCourseTeacherUserService extends IService<XryCourseTeacherUserEntity> {

    /**
     * app端用户加入课程学习的保存方法
     * @param params
     * @return
     */
    Integer saveCourse(Map<String,Object> params);

    /**
     * app端用户关注讲师的保存方法
     * @param params
     * @return
     */
    Integer saveTeacher(Map<String,Object> params);

    /**
     * app端根据用户查询用户加入学习的课程列表
     * @param params
     * @return
     */
    PageUtils appPageListCourseByUserId(Map<String, Object> params);

    /**
     * app端根据用户查询用户关注的讲师列表
     * @param params
     * @return
     */
    PageUtils appPageListTeacherByUserId(Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程
     * @param params
     */
    void appDelCourseById(Map<String, Object> params);

    /**
     * app端用户删除已经关注的讲师
     * @param params
     */
    void appDelTeacherById(Map<String, Object> params);

}
