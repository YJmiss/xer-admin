package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryCourseDao extends BaseMapper<XryCourseEntity> {

    /**
    * 查询课程树
    * @return
    */
   List<XryCourseEntity> treeCourseList();

    /**
     * 查询课程和课程目录信息
     * @param cid
     * @return
     */
    XryCourseEntity queryCourseAndCourseCat(Long cid);

    /**
     * 查询课程类目树
     * @return
     */
    List<XryCourseCatEntity> queryCourseCatList();

}
