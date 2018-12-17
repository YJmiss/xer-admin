package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
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
public interface XryUserDao extends BaseMapper<XryUserEntity> {


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
     * 重写删除
     * @param ids
     */
    void deleteBatchIds(@Param("ids")List<String> ids);

    /**
     * 构造用户树
     * @return
     */
    List<XryUserEntity> treeUser();

}
