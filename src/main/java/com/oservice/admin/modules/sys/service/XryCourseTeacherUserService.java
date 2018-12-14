package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCourseTeacherUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程报名和讲师关注表接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface XryCourseTeacherUserService extends IService<XryCourseTeacherUserEntity> {

    /**
     * app端用户加入课程学习的保存方法
     *
     * @param params
     * @return
     */
    Integer appSaveCourse(Map<String, Object> params);

    /**
     * app端用户关注讲师的保存方法
     *
     * @param params
     * @return
     */
    Integer appSaveTeacher(Map<String, Object> params);

    /**
     * app端根据用户查询用户加入学习的课程列表
     *
     * @param params
     * @return
     */
    PageUtils appPageListCourseByUserId(Map<String, Object> params);

    /**
     * app端根据用户查询用户关注的讲师列表
     *
     * @param params
     * @return
     */
    PageUtils appPageListTeacherByUserId(Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程
     *
     * @param params
     */
    void appDelCourseById(Map<String, Object> params);

    /**
     * app端用户删除已经关注的讲师
     *
     * @param params
     */
    void appDelTeacherById(Map<String, Object> params);

    /**
     * 根据课程id查询出报名学习该课程的所有用户
     *
     * @param courseId
     */
    List<String> listUserIdByCourseId(Long courseId);

    /**
     * 根据讲师id查询关注改讲师的所有用户
     *
     * @param teacherId
     * @return
     */
    List<String> listUserIdByTeacherId(String teacherId);
}
