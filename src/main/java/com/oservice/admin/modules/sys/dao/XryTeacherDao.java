package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryOrganizationEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryTeacherDao extends BaseMapper<XryTeacherEntity> {
    
    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String, Object> map);


    /**
     * 讲师认证
     * @param params
     */
    void recordExamineInfo(@Param("params") Map<String, Object> params);

}