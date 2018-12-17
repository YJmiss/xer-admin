package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryArticleEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
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
public interface XryArticleDao extends BaseMapper<XryArticleEntity> {

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
     * 发布文章、取消发布文章
     * @param params
     */
    void updateArticleStatus(@Param("params") Map<String,Object> params);

    /**
     * 推荐文章、取消推荐文章
     * @param params
     */
    void updateArticleRecommend(Map<String, Object> params);


}
