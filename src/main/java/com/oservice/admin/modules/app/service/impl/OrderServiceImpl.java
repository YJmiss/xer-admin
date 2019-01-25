package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.modules.app.dao.AppOrderDao;
import com.oservice.admin.modules.app.entity.AppUserPrderEntity;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import com.oservice.admin.modules.app.entity.XryOrderEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.app.service.DistributionService;
import com.oservice.admin.modules.app.service.OrderCourseService;
import com.oservice.admin.modules.app.service.OrderService;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @program: oservice
 * @description: 订单服务
 * @author: YJmiss
 * @create: 2018-12-19 09:33
 **/
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<AppOrderDao, XryOrderEntity> implements OrderService {
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private OrderCourseService orderCourseService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private DistributionService distributionService;
    @Resource
    private CartService cartService;

    @Override
    public void createOrder(long[] ids, XryUserEntity user, String sharingId) {
        List<XryOrderCourseEntity> orderCoursesList = new ArrayList<>();
        String id = redisUtils.getId();
        XryOrderEntity order = new XryOrderEntity();
        order.setOrderId(id);
        order.setStatus(1);//'状态：1、未付款，2、已付款(交易成功)，3、交易关闭',
        order.setCreateTime(new Date());// '订单创建时间'
        order.setUpdateTime(new Date());//'订单更新时间'
        order.setUserId(user.getId());//'用户id',
        order.setBuyerPhone(user.getPhone());//'买家电话',
        long totalFee = 0;//课程总金额
        for (long a : ids) {
            if ((!sharingId.equals("sharingId")) && (ids.length < 2)) {
                XryUserEntity dUser = new XryUserEntity();
                dUser.setId(sharingId);
                distributionService.createOrder(a, dUser, id);
            } else {
                XryCourseEntity xryCourseEntity = xryCourseService.queryById(a);
                XryOrderCourseEntity orderCourseEntity = new XryOrderCourseEntity();
                orderCourseEntity.setPicPath(xryCourseEntity.getImage());
                Long price = xryCourseEntity.getPrice();//价格
                orderCourseEntity.setPrice(price);
                totalFee += price;//记录总价格
                orderCourseEntity.setTitle(xryCourseEntity.getTitle());//标题
                orderCourseEntity.setId(UUIDUtils.getUUID());
                orderCourseEntity.setCourseId(a);
                orderCourseEntity.setOrderId(id);
                orderCoursesList.add(orderCourseEntity);
                if (redisUtils.hasKey("APPCART" + user.getId())) {
                    cartService.deleteCourse(user, a, id);
                }
            }
        }
        order.setTotalFee(totalFee);
        baseMapper.addOrder(order);
        orderCourseService.createOrderCourse(orderCoursesList);
    }

    @Override
    public void payOrder(String orderId, String money) {
        XryOrderEntity order = new XryOrderEntity();
        order.setOrderId(orderId);
        order.setStatus(2);
        order.setUpdateTime(new Date());
        order.setPaymentTime(new Date());
        order.setEndTime(new Date());
        order.setPayment(money);
        baseMapper.updatePayOrderByorId(order);
    }

   /* @Override
    public void closeOrder(String orderId) {
        XryOrderEntity order = new XryOrderEntity();
        order.setOrderId(orderId);
        order.setStatus(1);
        order.setUpdateTime(new Date());
        order.setCloseTime(new Date());
        baseMapper.updateById(order);
    }*/

    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> params = new HashMap<>();
        String pageNo = (String) param.get("page");
        String pageSize = (String) param.get("limit");
        String orderId = (String) param.get("orderId");
        String phone = (String) param.get("phone");
        String time = (String) param.get("createTime");
        String status = (String) param.get("status");
        if (null != time && !"".equals(time)) {
            String[] yearArr = time.split(" 年 ");
            String year = yearArr[0];
            String[] monthArr = yearArr[1].split(" 月 ");
            String month = monthArr[0];
            String day = monthArr[1].split(" 日")[0];
            time = year + "-" +month + "-" + day;
            params.put("created", "%" + time + "%");
        }
        params.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        params.put("pageSize", pageSize);
        params.put("orderId", orderId);
        params.put("phone", phone);
        params.put("status", status);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal1(params);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList1(params);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public List<String> getOrderIdByUserId(String id) {
        return baseMapper.getOrderIdByUserId(id);
    }

    @Override
    public void cancelOrder(String orderId) {
        XryOrderEntity order = new XryOrderEntity();
        order.setOrderId(orderId);
        order.setStatus(3);
        order.setUpdateTime(new Date());
        order.setCloseTime(new Date());
        baseMapper.updatecancelOrderByorId(order);
    }

    @Override
    public void deleteOrder(String orderId) {
        baseMapper.deleteOrderByorId(orderId);
        orderCourseService.deleteOrderCourseByorID(orderId);
    }

    @Override
    public Map<String, Object> getOrderByUserId(String id) {
        Map<String, Object> map = new HashMap<>();
        List<XryOrderEntity> orders = baseMapper.selectByUserId(id);
        for (XryOrderEntity order : orders) {
            AppUserPrderEntity appUserPrderEntity = new AppUserPrderEntity();
            List<XryOrderCourseEntity> orderCourses = orderCourseService.getOrderCourses(order.getOrderId());
            appUserPrderEntity.setOrderEntity(order);
            appUserPrderEntity.setOrderCourses(orderCourses);
            map.put(order.getOrderId(), appUserPrderEntity);
        }
        return map;
    }

    @Override
    public Map<String, Object> getUnpaidOrderByUserId(String id) {
        Map<String, Object> map = new HashMap<>();
        List<XryOrderEntity> paidOrders = baseMapper.getunpaidOrderByUserId(id);
        for (XryOrderEntity order : paidOrders) {
            AppUserPrderEntity appUserPrderEntity = new AppUserPrderEntity();
            List<XryOrderCourseEntity> orderCourses = orderCourseService.getOrderCourses(order.getOrderId());
            appUserPrderEntity.setOrderEntity(order);
            appUserPrderEntity.setOrderCourses(orderCourses);
            map.put(order.getOrderId(), appUserPrderEntity);
        }
        return map;
    }

    @Override
    public Map<String, Object> getPaidOrderByUserId(String id) {
        Map<String, Object> map = new HashMap<>();
        List<XryOrderEntity> paidOrders = baseMapper.getPaidOrderByUserId(id);
        for (XryOrderEntity order : paidOrders) {
            AppUserPrderEntity appUserPrderEntity = new AppUserPrderEntity();
            List<XryOrderCourseEntity> orderCourses = orderCourseService.getOrderCourses(order.getOrderId());
            appUserPrderEntity.setOrderEntity(order);
            appUserPrderEntity.setOrderCourses(orderCourses);
            map.put(order.getOrderId(), appUserPrderEntity);
        }
        return map;
    }

    @Override
    public Map<String, Object> getCloseOrderByUserId(String id) {
        Map<String, Object> map = new HashMap<>();
        List<XryOrderEntity> paidOrders = baseMapper.getCloseOrderByUserId(id);
        for (XryOrderEntity order : paidOrders) {
            AppUserPrderEntity appUserPrderEntity = new AppUserPrderEntity();
            List<XryOrderCourseEntity> orderCourses = orderCourseService.getOrderCourses(order.getOrderId());
            appUserPrderEntity.setOrderEntity(order);
            appUserPrderEntity.setOrderCourses(orderCourses);
            map.put(order.getOrderId(), appUserPrderEntity);
        }
        return map;
    }
}
