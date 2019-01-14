package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.entity.AppCartAndCollectEntity;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.app.service.DistributionService;
import com.oservice.admin.modules.app.service.OrderCourseService;
import com.oservice.admin.modules.app.service.OrderService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 订单控制器
 * @author: YJmiss
 * @create: 2018-12-19 09:16
 **/
@RestController
@RequestMapping("/api/appOrder")
@Api(description = "APP订单")
public class AppOrderController extends AbstractController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderCourseService orderCourseService;
    @Resource
    private CartService cartService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private DistributionService distributionService;
    /**
     * 后台列表所有订单
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("xry:order:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 订单详情
     */
    @GetMapping("/getOrderCourses")
    @RequiresPermissions("xry:order:info")
    public Result getOrderCourses(String orderId) {
        List<XryOrderCourseEntity> orderCourses = orderCourseService.getOrderCourses(orderId);
        return Result.ok().put("orderCourses", orderCourses);
    }

    /**@RequestParam long[] ids
     * 生成订单
     */
    @ApiOperation(value = "生成订单", notes = "从购物车提交订单")
    @PostMapping("/createOrder")
    public Result createOrder(long[] ids) {
        orderService.createOrder(ids, getAppUser());
        for (long courseId : ids) {         //移除购物车中生成订单的课程
            if (redisUtils.hasKey("APPCART" + getAppUserId())) {
                cartService.deleteCourse(getAppUser(), courseId);
            }
        }
        return Result.ok();
    }

    /**
     * @RequestParam long[] ids
     * 确认订单
     */
    @ApiOperation(value = "确认订单", notes = "从购物车到确认订单页面")
    @PostMapping("/affirmOrder")
    public Result affirmOrder(long[] ids) {
        Map<String, Object> map = new HashMap<>();
        List<AppCartAndCollectEntity> cartList = cartService.getCartListIsCollectFromRedis(getAppUser());
        List<AppCartAndCollectEntity> courses = new ArrayList<>();
        for (long courseId : ids) {
            if (cartList.size() > 0) {
                for (AppCartAndCollectEntity appCart : cartList) {
                    if (appCart.getId() == courseId || appCart.getId().equals(ids)) {
                        courses.add(appCart);
                    }
                }
                if (courses.size() < 1) {
                    XryCourseEntity xryCourseEntity = xryCourseService.queryById(courseId);
                    AppCartAndCollectEntity appCart = new AppCartAndCollectEntity();
                    appCart.setId(courseId);
                    appCart.setImage(xryCourseEntity.getImage());
                    appCart.setPrice(xryCourseEntity.getPrice());
                    appCart.setTitle(xryCourseEntity.getTitle());
                    appCart.setNickname(xryTeacherService.selectById(xryCourseEntity.getTid()).getRealName());
                    courses.add(appCart);
                }
            } else {
                XryCourseEntity xryCourseEntity = xryCourseService.queryById(courseId);
                AppCartAndCollectEntity appCart = new AppCartAndCollectEntity();
                appCart.setId(courseId);
                appCart.setImage(xryCourseEntity.getImage());
                appCart.setPrice(xryCourseEntity.getPrice());
                appCart.setTitle(xryCourseEntity.getTitle());
                appCart.setNickname(xryTeacherService.selectById(xryCourseEntity.getTid()).getRealName());
                courses.add(appCart);
            }
        }
        map.put("courses", courses);
        Long total = 0l;         //合计
        Long actuallyPaid = 0l;//TODO:实付款金额，计算优惠,目前直接返回实际价格
        for (AppCartAndCollectEntity appCart : courses) {
            total += appCart.getPrice();
            actuallyPaid += appCart.getPrice();
        }
        map.put("total", total);
        map.put("actuallyPaid", actuallyPaid);
        return Result.ok(map);
    }
    /**
     * 订单支付成功
     * TODO:用户付款成功后调用服务：cartService.payOrder(String orderId, String money) orderId:订单号/money:实际付款金额
     *
     */

    /**
     * 生成未付款订单
     *//*
    @ApiOperation(value = "用户离开支付页面订单状态为：待支付", notes = "待支付订单，用户可以在我的待支付订单里面查看")
    @GetMapping("/closeOrder")
    public Result closeOrder(String orderId) {
        orderService.closeOrder(orderId);
        return Result.ok();
    }*/


    /**
     * 生成取消订单
     */
    @ApiOperation(value = "用户取消订单，订单状态为关闭订单", notes = "用户点击取消订单")
    @GetMapping("/cancelOrder")
    public Result cancelOrder(String orderId) {
        orderService.cancelOrder(orderId);
        distributionService.createOrder1(32l, getAppUser(), "20190110001");
        return Result.ok();
    }

    /**
     * 删除订单
     */
    @ApiOperation(value = "用户在交易关闭页面删除订单", notes = "只能在交易关闭页面请求此接口")
    @GetMapping("/deleteOrder")
    public Result deleteOrder(String orderId) {
        orderService.deleteOrder(orderId);
        return Result.ok();
    }

    /**
     * APP用户订单列表
     */
    @ApiOperation(value = "进入我的订单页面请求接口", notes = "列表所有订单")
    @GetMapping("/orderList")
    public Result orderList() {
        Map<String, Object> orderByUserId = orderService.getOrderByUserId(getAppUserId());
        return Result.ok().put("list", orderByUserId);
    }

    /**
     * APP用户未支付订单列表
     */
    @ApiOperation(value = "进入我的订单页面点击未付款选项请求接口", notes = "列表所有未付款订单")
    @GetMapping("/unpaidorderList")
    public Result unpaidorderList() {
        Map<String, Object> orderByUserId = orderService.getUnpaidOrderByUserId(getAppUserId());
        return Result.ok().put("list", orderByUserId);
    }

    /**
     * APP用户交易成功订单列表
     */
    @ApiOperation(value = "进入我的订单页面点击交易成功选项请求接口", notes = "列表交易成功订单")
    @GetMapping("/paidorderList")
    public Result paidorderList() {
        Map<String, Object> orderByUserId = orderService.getPaidOrderByUserId(getAppUserId());
        return Result.ok().put("list", orderByUserId);
    }

    /**
     * APP用户交易关闭订单列表
     */
    @ApiOperation(value = "进入我的订单页面点击交易关闭选项请求接口", notes = "列表交易关闭订单")
    @GetMapping("/closeorderList")
    public Result closeorderList() {
        Map<String, Object> orderByUserId = orderService.getCloseOrderByUserId(getAppUserId());
        return Result.ok().put("list", orderByUserId);
    }
}
