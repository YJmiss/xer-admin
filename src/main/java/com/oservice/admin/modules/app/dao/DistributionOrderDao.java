package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.DistributionOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface DistributionOrderDao extends BaseMapper<DistributionOrder> {
    /**
     * 查询返回的数据总数
     *
     * @param map
     * @return
     */
    Long countTotal1(@Param("params") Map<String, Object> map);


    /**
     * 自定义分页查询
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList1(@Param("params") Map<String, Object> map);


    void insertDistribution(DistributionOrder distribution);

    Long myEarnings(String appUserId);

    List<DistributionOrder> accountBalance(String appUserId);

    void updateStatusByUid(String appUserId);

    Integer getOkNumByUidAndCid(DistributionOrder distributionOrder);
}
