package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.SysUserEntity;
import com.oservice.admin.modules.sys.entity.XryPayOfflineEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 线下支付表
 *
 * @author LingDu
 * @version 1.0
 */
@Mapper
public interface XryPayOfflineDao extends BaseMapper<XryPayOfflineEntity> {
	
	/**
	 * 查询返回的数据总数
	 * @param map
	 * @return
	 */
	Long countTotal(@Param("params") Map<String,Object> map);

	/**
	 * 自定义分页查询
	 * @param map
	 * @return
	 */
	List<Map<String, Object>> pageList(@Param("params") Map<String,Object> map);

}
