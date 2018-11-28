package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
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
public interface XryCourseCatDao extends BaseMapper<XryCourseCatEntity> {

    /**
     * 查询类目树
     * @return
     */
    List<XryCourseCatEntity> treeCourseCat();

    /**
     * 查询课程信息
     * @param id
     * @return
     */
    List<XryCourseEntity> listCourseByCourseCatalogId(Long id);

    /**
     * 查询类目信息
     * @param id
     * @return
     */
    List<XryCourseCatEntity> isParentCourseCatalogById(Long id);

    /**
     *  课程类目的启用、禁用
     * @param params
     */
    void updateCourseCatStatus(Map<String, Object> params);

}
