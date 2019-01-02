package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
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
public interface XryVideoDao extends BaseMapper<XryVideoEntity> {

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
     * 视频的审核
     * @param params
     */
   void recordExamineInfo(@Param("params") Map<String, Object> params);

    /**
     * 判断与之关联的“视频”的资料是否已填
     * @param catalogId
     * @return
     */
    List<XryVideoEntity> judeVideoIsFullByCourseId(@Param("id") Long catalogId);
}
