package com.oservice.admin.modules.app.service;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.app.entity.WithdrawalRecord;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: YJmiss
 * @Date: 2019/1/29
 */
public interface WithdrawalRecordService {

    void informationSave(WithdrawalRecord withdrawalRecord);

    List<Map<String, Object>> getWithdrawalRecordByUid(String appUserId);

    /**
     * 后台列表所有记录
     *
     * @param
     */
    PageUtils queryPage(Map<String, Object> params);

    Boolean updateWithdrawal(String id);
}
