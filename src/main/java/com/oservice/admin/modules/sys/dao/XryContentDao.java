package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 * 广告内容表的DAO
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryContentDao extends BaseMapper<XryContentEntity> {


    List<XryContentEntity> getContentsByCat(int cat);

}
