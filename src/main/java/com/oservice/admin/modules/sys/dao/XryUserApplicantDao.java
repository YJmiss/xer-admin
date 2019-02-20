package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.modules.sys.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryUserApplicantDao extends BaseMapper<XryUserApplicantEntity> {

    /**
     * app端根据用户查询用户加入学习的课程列表
     * @param params
     * @return
     */
    List<Map<String, Object>> appPageListCourseByUserId(@Param("params") Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程
     * @param params
     */
    void appDelCourseById(@Param("params") Map<String, Object> params);

    /**
     * 
     * @param courseId
     * @return
     */
    List<String> listUserIdByCourseId(Long courseId);
    
    /**
     * 查询课程的报名人数列表
     * @param courseDetailId
     * @return
     */
    List<XryUserApplicantEntity> countApplicantByCourseId(@Param("courseId") Long courseDetailId);

    /**
     * 根据用户id和课程id查询该用户是否报名了该课程
     * @param params
     * @return
     */
    XryUserApplicantEntity isApplicantByUserIdAndCourseId(@Param("params") Map<String, Object> params);

    /**
     * 清除最近学习课程
     * @param userId
     */
    void removeUserCourseByUserId(@Param("userId") String userId);

    /**
     * app保存用户课程学习进度
     * @param params
     */
    void addCourseStudyProgress(@Param("params") Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程，通过用户id和课程id
     * @param params
     */
    void delCourseByUserIdAndCourseId(@Param("params") Map<String, Object> params);
}
