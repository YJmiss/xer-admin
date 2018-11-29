package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 广告内容表的DAO
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryContentDao extends BaseMapper<XryContentEntity> {

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String,Object> map);

    /**
     * 广告禁用、启用
     * @param params
     */
    void updateContentStatus(Map<String, Object> params);

}
