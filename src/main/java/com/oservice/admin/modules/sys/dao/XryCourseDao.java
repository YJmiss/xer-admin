package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
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
public interface XryCourseDao extends BaseMapper<XryCourseEntity> {


    /**
     * 查询返回的数据总数
     * @param map
     * @return
     */
    Long countTotal(@Param("params") Map<String,Object> map);

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String,Object> map);

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> examineList(@Param("params") Map<String,Object> map);

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

    /**
     * 课程的审核
     * @param params
     */
   void recordExamineInfo(@Param("params") Map<String, Object> params);

    /**
     * 获取索引数据
     *
     * @param
     */
    List<SearcherItem> findAllItems();

    /**
     * 通过ID获取索引数据
     *
     * @param
     */
    SearcherItem findItemsById(Long id);

    /**
     * 推荐课程、取消推荐课程
     * @param params
     */
    void updateCourseRecommend(Map<String, Object> params);

    /**
     * 查询用户已经选择喜好的课程
     * @return
     */
    XryRecommendEntity listRecommendCourseCatByUserId(Map<String, Object> params);

    /**
     * 用户喜好添加
     * @param params
     */
    void appInsertRecommend(Map<String, Object> params);

    /**
     * 用户喜好修改
     * @param params
     */
    void appUpdateRecommend(Map<String, Object> params);

}
