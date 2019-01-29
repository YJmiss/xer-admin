package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.WithdrawalRecord;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface WithdrawalRecordDao extends BaseMapper<WithdrawalRecord> {

    List<WithdrawalRecord> getWithdrawalRecordByUid(String appUserId);

    Long countTotal2(Map<String, Object> params1);

    List<Map<String, Object>> pageList2(Map<String, Object> params1);

    Integer updateWithdrawal(String id);
}
