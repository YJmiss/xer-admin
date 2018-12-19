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
    List<Map<String, Object>> appPageListCourseByUserId(Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程
     * @param params
     */
    void appDelCourseById(Map<String, Object> params);

    /**
     * 
     * @param courseId
     * @return
     */
    List<XryUserApplicantEntity> listUserIdByCourseId(Long courseId);
}