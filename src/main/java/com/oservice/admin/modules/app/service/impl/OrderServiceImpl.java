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

    @Override
    public void createOrder(long[] ids, XryUserEntity user) {
        List<XryOrderCourseEntity> orderCoursesList = new ArrayList<>();
       /* //1.1）为了让订单号不重复，一般使用 redis 的 incr 命令自增长生成订单编号
        if(!jedisClient.exists(ConfigConstant.ORDERID)){ //为订单号设置一个默认值
            jedisClient.set(ConfigConstant.ORDERID, "54000");
        }
        //1.2)将上面的自动增长的 id 设置给 orderInfo 的 id
        String id = jedisClient.incr(ConfigConstant.ORDERID).toString();*/
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
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String orderId = (String) params.get("orderId");
        String phone = (String) params.get("phone");
        String time = (String) params.get("createTime");
        String status = (String) params.get("status");
        if (null != time && !"".equals(time)) {
            String[] yearArr = time.split(" 年 ");
            String year = yearArr[0];
            String[] monthArr = yearArr[1].split(" 月 ");
            String month = monthArr[0];
            String day = monthArr[1].split(" 日")[0];
            time = year + "-" +month + "-" + day;
            map.put("created", "%" + time + "%");
        }
        map.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize", pageSize);
        map.put("orderId", orderId);
        map.put("phone", phone);
        map.put("status", status);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal1(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList1(map);
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
