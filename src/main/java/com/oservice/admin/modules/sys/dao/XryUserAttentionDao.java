package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;
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
public interface XryUserAttentionDao extends BaseMapper<XryUserAttentionEntity> {

    /**
     * app端根据用户查询用户关注的讲师列表
     * @param params
     * @return
     */
    List<Map<String, Object>> appPageListTeacherByUserId(Map<String, Object> params);

    /**
     * app端用户删除已经关注的讲师
     * @param params
     */
    void appDelTeacherById(@Param("params") Map<String, Object> params);

    /**
     * 根据讲师id查询关注改讲师的所有用户
     * @param teacherId
     * @return
     */
    List<XryUserAttentionEntity> listUserIdByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 查询讲师的关注人数列表
     * @param teacherId
     * @return
     */
    List<XryUserAttentionEntity> countAttentionByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 根据用户id和讲师id查询该用户是否关注了该讲师
     * @param params
     * @return
     */
    XryUserAttentionEntity isAttentionByUserIdAndTeacherId(@Param("params") Map<String, Object> params);
}
