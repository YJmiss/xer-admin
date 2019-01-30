package com.oservice.admin.modules.app.service;

import com.oservice.admin.modules.app.entity.CourseClick;

import java.util.List;

/**
 * @program: oservice
 * @description: 课程封校点击记录服务
 * @author: YJmiss
 * @create: 2019-01-30 10:34
 **/
public interface CourseClickService {

    List<CourseClick> selectClickByUidAndCid(CourseClick courseClick);

    Boolean courseClickSave(CourseClick courseClick);

    void updateClick(CourseClick courseClick);

    Integer getCountss(CourseClick courseClick);

    List<CourseClick> selectClickByUid(String appUserId);
}
