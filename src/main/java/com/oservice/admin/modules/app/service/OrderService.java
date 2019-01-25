package com.oservice.admin.modules.app.service;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserEntity;

import java.util.List;
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
    void createOrder(long[] ids, XryUserEntity user, String sharingId);

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
    // void closeOrder(String orderId);

    /**
     * 后台列表所有订单
     *
     * @param
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户已购买的订单
     *
     * @param
     */
    List<String> getOrderIdByUserId(String id);

    /**
     * 未付款或支付失败致交易关闭
     *
     * @param
     */
    void cancelOrder(String orderId);

    /**
     * 删除订单
     *
     * @param
     */
    void deleteOrder(String orderId);

    /**
     * 列表用户订单
     *
     * @param
     */
    Map<String, Object> getOrderByUserId(String id);

    /**
     * 列表用户未付款订单
     *
     * @param
     */
    Map<String, Object> getUnpaidOrderByUserId(String id);

    /**
     * 列表用户交易成功订单
     *
     * @param
     */
    Map<String, Object> getPaidOrderByUserId(String id);

    /**
     * 列表用户交易关闭订单
     *
     * @param
     */
    Map<String, Object> getCloseOrderByUserId(String id);

}
