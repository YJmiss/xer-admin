package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
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
public interface XryCourseDescDao extends BaseMapper<XryCourseDescEntity> {

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
     * 重写查询
     * @param id
     * @return
     */
    XryCourseDescEntity queryById(Long id);

    /**
     * 重写修改
      * @param xryCourseDescEntity
     */
    Integer updateById(XryCourseDescEntity xryCourseDescEntity);

    /**
     * 重写删除
     * @param courseIds
     */
    void deleteBatchIds(@Param("courseIds")List<Long> courseIds);

    /**
     * 重写删除
     *
     * @param id
     */
    void deleteDescById(Long id);
}
