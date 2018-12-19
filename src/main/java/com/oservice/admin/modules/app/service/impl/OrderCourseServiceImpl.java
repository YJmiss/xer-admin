package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.app.dao.OrderCourseDao;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import com.oservice.admin.modules.app.service.OrderCourseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: oservice
 * @description: 订单详情
 * @author: YJmiss
 * @create: 2018-12-19 10:19
 **/
@Service("orderCourseService")
public class OrderCourseServiceImpl extends ServiceImpl<OrderCourseDao, XryOrderCourseEntity> implements OrderCourseService {
    @Override
    public List<XryOrderCourseEntity> getOrderCourses(String orderId) {
        return baseMapper.getOrderCourses(orderId);
    }

    @Override
    public void createOrderCourse(List<XryOrderCourseEntity> list) {
        for (XryOrderCourseEntity courses : list) {
            baseMapper.addOrderCourses(courses);
        }
    }

}
