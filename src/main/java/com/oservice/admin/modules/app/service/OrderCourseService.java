package com.oservice.admin.modules.app.service;

import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;

import java.util.List;

/**
 * @Description: 订单详情
 * @Param:
 * @return:
 * @Author: YJmiss
 * @Date: 2018/12/19
 */
public interface OrderCourseService {
    /**
     * 根据订单ID 列表订单下的课程
     *
     * @param
     */
    List<XryOrderCourseEntity> getOrderCourses(String orderId);

    /**
     * 生成订单对应详情
     *
     * @param
     */
    void createOrderCourse(List<XryOrderCourseEntity> list);

    /**
     * 删除订单对应详情
     *
     * @param
     */
    void deleteOrderCourseByorID(String orderId);

}
