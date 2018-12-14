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
     * 构造讲师树
     * @return
     */
    List<XryUserEntity> treeUser();

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
     * 讲师推荐、取消推荐
     * @param params
     */
    void updateUserRecommend(Map<String, Object> params);


}
