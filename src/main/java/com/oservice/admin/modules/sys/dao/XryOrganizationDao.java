package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryOrganizationEntity;
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
public interface XryOrganizationDao extends BaseMapper<XryOrganizationEntity> {

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
    List<Map<String, Object>> pageList(@Param("params") Map<String, Object> map);


    /**
     * 机构认证
     * @param params
     */
    void recordExamineInfo(@Param("params") Map<String, Object> params);

    /**
     * 根据机构id查询机构
     *
     * @param id
     * @return
     */
    Map<String, Object> queryById(Long id);

    /**
     * app查询机构列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> listByUserId(Map<String, Object> params);


}
