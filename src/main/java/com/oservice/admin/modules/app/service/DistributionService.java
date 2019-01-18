package com.oservice.admin.modules.app.service;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserEntity;

import java.util.Map;

public interface DistributionService {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 一级分销订单生成
     *
     * @param id:课程ID user:用户 orderId:订单ID
     */
    void createOrder(Long id, XryUserEntity user, String orderId);
}
