package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.app.dao.CourseClickDao;
import com.oservice.admin.modules.app.entity.CourseClick;
import com.oservice.admin.modules.app.service.CourseClickService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: oservice
 * @description:
 * @author: YJmiss
 * @create: 2019-01-30 10:36
 **/
@Service("courseClickService")
public class CourseClickServiceImpl extends ServiceImpl<CourseClickDao, CourseClick> implements CourseClickService {

    @Override
    public List<CourseClick> selectClickByUidAndCid(CourseClick courseClick) {
        List<CourseClick> courseClicks = baseMapper.selectClickByUidAndCid(courseClick);
        if (courseClicks == null || courseClicks.size() < 1) {
            return null;
        }
        return courseClicks;
    }

    @Override
    public Boolean courseClickSave(CourseClick courseClick) {
        Integer c = baseMapper.courseClickSave(courseClick);
        if (c > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateClick(CourseClick courseClick) {
        baseMapper.updateClick(courseClick);
    }

    @Override
    public Integer getCountss(CourseClick courseClick) {
        List<CourseClick> courseClicks = baseMapper.selectClickByUidAndCid(courseClick);
        if (courseClicks == null || courseClicks.size() < 1) {
            return 0;
        } else {
            return courseClicks.get(0).getCountss();
        }
    }

    @Override
    public List<CourseClick> selectClickByUid(String appUserId) {
        List<CourseClick> courseClicks = baseMapper.selectClickByUid(appUserId);
        if (courseClicks == null || courseClicks.size() < 1) {
            return null;
        } else {
            return courseClicks;
        }
    }
}
