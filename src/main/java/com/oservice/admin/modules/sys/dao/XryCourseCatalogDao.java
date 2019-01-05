package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
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
public interface XryCourseCatalogDao extends BaseMapper<XryCourseCatalogEntity> {

    /**
     * 查询返回的数据总数page.totalCount
     * @param map
     * @return
     */
    Long countTotal(@Param("params") Map<String, Object> map);

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String,Object> map);

    /**
     * 查询课程目录树
     * @return
     */
    List<XryCourseCatalogEntity> treeCourseCatalog(Long courseId);

    /**
     * 判断与之关联的“目录”的资料是否已填
     * @param id
     * @return
     */
    List<XryCourseCatalogEntity> judeCatalogIsFullByCourseId(@Param("id") Long id);

    /**
     * 删除目录之前判断目录是否存在视频
     * @param id
     * @return
     */
    List<XryVideoEntity> listVideoByCatalogId(@Param("id") Long id);
}
