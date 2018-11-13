package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
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
public interface XryCourseDescDao extends BaseMapper<XryCourseDescEntity> {

    /**
     * 根据id查询
     * @param courseId
     * @return
     */
    XryCourseDescEntity selectByKey(Long courseId);

    /**
    * 根据id查询
    * @param courseId
    * @return
    */
   void deleteByKey(Long courseId);
   
}
