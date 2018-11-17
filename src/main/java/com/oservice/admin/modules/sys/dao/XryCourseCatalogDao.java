package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
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
public interface XryCourseCatalogDao extends BaseMapper<XryCourseCatalogEntity> {

    /**
     * 查询课程目录树
     * @return
     */
    List<XryCourseCatalogEntity> treeCourseCatalog();

    /**
     * 目录上、下架
     * @param params
     */
    void updateCourseCatalogStatus(Map<String,Object> params);

}
