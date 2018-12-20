package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.app.service.OrderCourseService;
import com.oservice.admin.modules.app.service.OrderService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    /**
     * 列表所有订单
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
    /**
     * 生成订单
     */
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody long[] ids) {
        orderService.createOrder(ids, getAppUser());
        for (long courseId : ids) {         //移除购物车中生成订单的课程
            cartService.deleteCourse(getAppUser(), courseId);
        }
        return Result.ok();
    }

    /**
     * 未付款订单（交易关闭）
     */
    @GetMapping("/closeOrder")
    public Result closeOrder(String orderId) {
        orderService.closeOrder(orderId);
        return Result.ok();
    }

    /**
     * 订单支付
     */

}
