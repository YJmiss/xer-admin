package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryCourseCatalogDao extends BaseMapper<XryCourseCatalogEntity> {
	
    List<XryCourseCatalogEntity> treeCourseCatalog();

}
