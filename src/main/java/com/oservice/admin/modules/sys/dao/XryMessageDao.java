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


}
