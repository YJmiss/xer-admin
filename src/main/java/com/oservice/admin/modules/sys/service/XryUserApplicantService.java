package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserApplicantEntity;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户报名表接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface XryUserApplicantService extends IService<XryUserApplicantEntity> {

    /**
     * app端用户加入课程学习的保存方法
     * @param params
     * @return
     */
    Integer appSaveCourse(Map<String, Object> params);

    /**
     * app端根据用户查询用户加入学习的课程列表
     * @param params
     * @return
     */
    PageUtils appPageListCourseByUserId(Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程
     * @param params
     */
    void appDelCourseById(Map<String, Object> params);

    /**
     * 根据课程id查询出报名学习该课程的所有用户
     *
     * @param courseId
     */
    List<String> listUserIdByCourseId(Long courseId);

    /**
     * 查询课程的报名人数列表
     * @param courseDetailId
     * @return
     */
    List<XryUserApplicantEntity> countApplicantByCourseId(Long courseDetailId);

    /**
     * 根据用户id和课程id查询该用户是否报名了该课程
     * @param courseId
     * @param userId
     * @return
     */
    XryUserApplicantEntity isApplicantByUserIdAndCourseId(Long courseId, String userId);

    /**
     * 清除最近学习课程
     * @param userId
     */
    void removeUserCourseByUserId(String userId);

    /**
     * app保存用户课程学习进度
     * @param params
     */
    void addCourseStudyProgress(Map<String, Object> params);
}
