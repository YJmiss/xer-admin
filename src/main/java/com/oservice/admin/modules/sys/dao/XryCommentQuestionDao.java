package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCommentQuestionEntity;
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
public interface XryCommentQuestionDao extends BaseMapper<XryCommentQuestionEntity> {

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
     * 修改常见问题的状态
     * @param params
     */
    void updateQuestionStatus(@Param("params") Map<String, Object> params);
}
