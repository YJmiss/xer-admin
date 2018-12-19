package com.oservice.admin.modules.app.service;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserEntity;

import java.util.Map;

/**
 * @Description:
 * @Param:
 * @return:
 * @Author: YJmiss
 * @Date: 2018/12/19
 */
public interface OrderService {
    /**
     * 订单生成
     *
     * @param
     */
    void createOrder(long[] ids, XryUserEntity user);

    /**
     * 付款成功
     *
     * @param
     */
    void payOrder(String orderId);

    /**
     * 超出时间未付款致交易关闭
     *
     * @param
     */
    void closeOrder(String orderId);

    /**
     * 后台列表所有订单
     *
     * @param
     */
    PageUtils queryPage(Map<String, Object> params);


}
