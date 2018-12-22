package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryOrderEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AppOrderDao extends BaseMapper<XryOrderEntity> {
    Integer addOrder(XryOrderEntity order);

    List<String> getOrderIdByUserId(String id);

    List<XryOrderEntity> selectByUserId(String id);

    void deleteOrderByorId(String orderId);

    List<XryOrderEntity> getunpaidOrderByUserId(String id);

    List<XryOrderEntity> getPaidOrderByUserId(String id);

    List<XryOrderEntity> getCloseOrderByUserId(String id);

}
