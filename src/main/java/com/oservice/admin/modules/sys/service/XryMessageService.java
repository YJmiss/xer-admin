package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;

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
     * @param messageId
     */
    Map<String, Object> seleMessageById(Long messageId);

    /**
     * 保存记录后返回自增的id
     * @param message
     * @return
     */
    Long saveAndGetId(XryMessageEntity message);
}
