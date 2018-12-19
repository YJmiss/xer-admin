package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderCourseDao extends BaseMapper<XryOrderCourseEntity> {
    /**
     * 根据订单ID 列表订单下的课程
     *
     * @param
     */
    List<XryOrderCourseEntity> getOrderCourses(String orderId);

    /**
     * 生成订单详情
     *
     * @param
     */
    Integer addOrderCourses(XryOrderCourseEntity courses);
}
