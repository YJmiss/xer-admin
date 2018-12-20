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
     * @param orderId :订单号
     * @param money : 实付款金额
     */
    void payOrder(String orderId, String money);

    /**
     * 未付款或支付失败致交易关闭
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
