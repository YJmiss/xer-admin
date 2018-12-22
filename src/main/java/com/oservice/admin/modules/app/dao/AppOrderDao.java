package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryOrderEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AppOrderDao extends BaseMapper<XryOrderEntity> {
    Integer addOrder(XryOrderEntity order);

    List<String> getOrderIdByUserId(String id);

    List<XryOrderEntity> selectByUserId(String id);

    void deleteOrderByorId(String orderId);

    List<XryOrderEntity> getunpaidOrderByUserId(String id);

    List<XryOrderEntity> getPaidOrderByUserId(String id);

    List<XryOrderEntity> getCloseOrderByUserId(String id);

    /**
     * 查询返回的数据总数
     *
     * @param map
     * @return
     */
    Long countTotal1(@Param("params") Map<String, Object> map);


    /**
     * 自定义分页查询
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList1(@Param("params") Map<String, Object> map);
}
