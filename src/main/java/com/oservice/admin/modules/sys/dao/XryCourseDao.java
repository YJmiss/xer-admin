package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryCourseDao extends BaseMapper<XryCourseEntity> {

    /**
     * 构造课程树
     * @return
     */
    List<XryCourseEntity> treeCourse();

    /**
     * 查询课程目录
     * @param id
     * @return
     */
    XryCourseCatalogEntity queryCourseCatalogByCourseId(Long id);

    /**
     * 查询课程描述
     * @param id
     * @return
     */
    XryCourseDescEntity queryCourseDescById(Long id);

    /**
     * 课程上、下架
     * @param params
     */
    void updateCourseStatus(Map<String,Object> params);

}
