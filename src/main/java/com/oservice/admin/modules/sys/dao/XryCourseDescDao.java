package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
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
    void deleteBatchIds(List<Long> courseIds);
}
