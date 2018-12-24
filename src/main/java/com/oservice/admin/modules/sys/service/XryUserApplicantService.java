package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserApplicantEntity;

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
     * 查询出消息发送的用户id
     * @param id
     * @return
     */
    List<Map<String, Object>> listUserIdByMsgId(Long id);
}
