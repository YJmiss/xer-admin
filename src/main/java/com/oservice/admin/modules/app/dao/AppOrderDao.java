package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryOrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppOrderDao extends BaseMapper<XryOrderEntity> {
    Integer addOrder(XryOrderEntity order);
}
