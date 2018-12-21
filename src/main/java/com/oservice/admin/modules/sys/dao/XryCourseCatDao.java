package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
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
     * 查询所有的课程类目
     * @return
     */
    List<Map<String, Object>> listCourseCat();

    /**
     * 修改类目状态
     * @param params
     */
    void updateCourseCatStatusByCatId(@Param("params") Map<String, Object> params);

    /**
     * 是父类目，查询出所有子类目
     * @param courseCatId
     * @return
     */
    String[] listCourseCatByParentCatId(@Param("courseCatId") Long courseCatId);
}
