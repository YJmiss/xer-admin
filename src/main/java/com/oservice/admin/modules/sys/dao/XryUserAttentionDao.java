package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;
import org.apache.ibatis.annotations.Mapper;

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
    void appDelTeacherById(Map<String, Object> params);

    /**
     * 
     * @param teacherId
     * @return
     */
    List<XryUserAttentionEntity> listUserIdByTeacherId(String teacherId);
}
