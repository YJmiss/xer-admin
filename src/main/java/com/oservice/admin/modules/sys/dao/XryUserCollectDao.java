package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryUserCollectEntity;
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
public interface XryUserCollectDao extends BaseMapper<XryUserCollectEntity> {

    /**
     * app端查询用户收藏列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> appListUserCollectByUserId(@Param("userId") String userId);

    /**
     * 根据课程id查询课程报名人数
     * @param courseId
     * @return
     */
    Integer countCourseApplicantByCourseId(@Param("courseId") Long courseId);
}
