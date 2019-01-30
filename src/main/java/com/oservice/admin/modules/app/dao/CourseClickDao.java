package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.CourseClick;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseClickDao extends BaseMapper<CourseClick> {

    List<CourseClick> selectClickByUidAndCid(CourseClick courseClick);

    void updateClick(CourseClick courseClick);

    Integer courseClickSave(CourseClick courseClick);

    List<CourseClick> selectClickByUid(String appUserId);
}
