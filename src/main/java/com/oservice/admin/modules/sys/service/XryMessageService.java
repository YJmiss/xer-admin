package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 消息表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryMessageService extends IService<XryMessageEntity> {

    /**
     * 消息分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据消息id，查询消息
     *
     * @param id
     * @return
     */
    XryMessageEntity queryById(Long id);

    /**
     * 记录保存
     *
     * @param xryMessageEntity
     */
    Integer save(XryMessageEntity xryMessageEntity);

    /**
     * 消息修改
     *
     * @param xryMessageEntity
     */
    void update(XryMessageEntity xryMessageEntity);

    /**
     * 消息删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 发布消息、取消发布
     * @param params
     */
    void updateMessageStatus(Map<String, Object> params);

    /**
     * 查询出当前保存的记录并发消息到客户端
     *
     * @param messageId
     */
    Map<String, Object> seleMessageById(Long messageId);

    /**
     * 保存记录后返回自增的id
     *
     * @param message
     * @return
     */
    Long saveAndGetId(XryMessageEntity message);

    /**
     * 查询用户课程未读消息数
     * @param params
     * @return
     */
    Integer countCourseMessageByUserId(Map<String, Object> params);

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
    List<Map<String, Object>> listCourseMessageByUserId(Map<String, Object> params);

    /**
     * 查询用户讲师消息列表，包括已读和未读
     * @param params
     * @return
     */
    List<Map<String, Object>> listTeacherMessageByUserId(Map<String, Object> params);

    /**
     * 查询平台消息列表
     * @return
     */
    List<Map<String, Object>> listSystemMessage();

    /**
     * 查询消息对象信息返回（如有需要）
     * @param params
     * @return
     */
    Map<String, Object> readUserMessageByUserId(Map<String, Object> params);

    /**
     * 读取消息后，把置为已读状态（字段read_status设为1）
     * @param messageId
     */
    void updateReadStatusByMessageId(Long messageId);
}
