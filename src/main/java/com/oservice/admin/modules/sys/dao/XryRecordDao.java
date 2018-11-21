package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryRecordDao extends BaseMapper<XryRecordEntity> {

    /**
     * 记录课程审核
     * @param params
     */
   void recordCourseExamine(Map<String, Object> params);
}
