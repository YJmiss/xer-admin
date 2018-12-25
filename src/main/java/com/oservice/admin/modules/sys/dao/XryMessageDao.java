package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
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
public interface XryMessageDao extends BaseMapper<XryMessageEntity> {

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
     * 发布消息、取消发布
     * @param params
     */
    void updateMessageStatus(Map<String, Object> params);

    /**
     * 保存记录后返回自增的id
     *
     * @param message
     * @return
     */
    Long saveAndGetId(XryMessageEntity message);

    /**
     * 查询出当前保存的记录并发消息到客户端
     *
     * @param messageId
     * @return
     */
    Map<String, Object> seleMessageById(Long messageId);

    /**
     * 查询用户课程未读消息数
     * @param params
     * @return
     */
    Integer countCourseMessageByUserId(@Param("params") Map<String, Object> params);

    /**
     * 查询用户我关注的讲师未读消息数
     * @param params
     * @return
     */
    Integer countTeacherMessageByUserId(@Param("params") Map<String, Object> params);


    /**
     * 查询系统未读消息数
     * @return
     */
    Integer countSystemMessage();

    /**
     * 查询用户课程消息列表，包括已读和未读
     * @param params
     * @return
     */
    List<Map<String, Object>> listCourseMessageByUserId(@Param("params") Map<String, Object> params);

    /**
     * 查询用户讲师消息列表，包括已读和未读
     * @param params
     * @return
     */
    List<Map<String, Object>> listTeacherMessageByUserId(@Param("params") Map<String, Object> params);

    /**
     * 查询平台消息列表
     * @return
     */
    List<Map<String, Object>> listSystemMessage();
}
