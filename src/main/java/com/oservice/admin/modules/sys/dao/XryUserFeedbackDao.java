package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;
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
public interface XryUserFeedbackDao extends BaseMapper<XryUserFeedbackEntity> {

    /**
     * 计算列表总数
     * @param map
     * @return
     */
    Long countTotal(@Param("params") Map<String, Object> map);

    /**
     * 获取分页列表
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String, Object> map);

    /**
     * app查询我的反馈列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> appListUserFeedbackByUserId(@Param("userId") String userId);

    /**
     * 反馈信息
     * @param id
     * @return
     */
    Map<String, Object> queryByIdAndUserId(@Param("id") Long id);
}
