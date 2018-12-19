package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.service.OrderCourseService;
import com.oservice.admin.modules.app.service.OrderService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @program: oservice
 * @description: 订单控制器
 * @author: YJmiss
 * @create: 2018-12-19 09:16
 **/
@RestController
@RequestMapping("/api/appCart")
@Api(description = "APP订单")
public class AppOrderController extends AbstractController {
    @Resource
    private OrderService orderService;
    @Resource
    private OrderCourseService orderCourseService;

    /**
     * 列表所有订单
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("app:order:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 生成订单
     */
    @PostMapping("/createOrder")
    public Result createOrder(@RequestBody long[] ids) {
        //    try {
        orderService.createOrder(ids, getAppUser());
        //    }catch (Exception e){
        //       return Result.error(500,"系统错误，请联系管理员！");
        //    }
        return Result.ok();
    }
}
