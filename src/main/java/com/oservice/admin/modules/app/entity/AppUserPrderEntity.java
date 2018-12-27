package com.oservice.admin.modules.app.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @program: oservice
 * @description:
 * @author: YJmiss
 * @create: 2018-12-26 15:19
 **/
public class AppUserPrderEntity implements Serializable {
    private XryOrderEntity orderEntity;
    private List<XryOrderCourseEntity> orderCourses;

    public XryOrderEntity getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(XryOrderEntity orderEntity) {
        this.orderEntity = orderEntity;
    }

    public List<XryOrderCourseEntity> getOrderCourses() {
        return orderCourses;
    }

    public void setOrderCourses(List<XryOrderCourseEntity> orderCourses) {
        this.orderCourses = orderCourses;
    }
}
