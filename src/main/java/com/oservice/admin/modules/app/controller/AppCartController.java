package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.entity.AppCartEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.service.XryUserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 购物车控制器
 * @author: YJmiss
 * @create: 2018-12-18 11:19
 **/
@RestController
@RequestMapping("/api/appCart")
@Api(description = "APP购物车")
public class AppCartController extends AbstractController {
    @Resource
    private CartService cartService;
    @Resource
    private XryUserCollectService xryUserCollectService;

    @ApiOperation(value = "添加到购物车", notes = "用户添加课程到购物车 courseId：课程ID")
    @GetMapping("/addCart")
    public Result addCart(long courseId) {
        cartService.addCart(getAppUser(), courseId);
        return Result.ok();
    }

    @ApiOperation(value = "移除购物车课程", notes = "用户移除购物车课程 courseId：课程ID")
    @GetMapping("/removeCart")
    public Result removeCart(long courseId) {
        cartService.deleteCourse(getAppUser(), courseId);
        return Result.ok();
    }

    @ApiOperation(value = "用户清空购物车", notes = "清空购物车")
    @GetMapping("/deleteCart")
    public Result deleteCart() {
        cartService.removeCart(getAppUser());
        return Result.ok();
    }

    @ApiOperation(value = "列表所有购物车", notes = "用户查看购物车")
    @GetMapping("listCart")
    public Result getCartListFromRedis() {
        Map<String, Object> map = new HashMap<>();
        List<AppCartEntity> cartList = cartService.getCartListFromRedis(getAppUser());
        if (cartList == null) {
            return Result.error(203, "购物车是空的哦！");
        } else {
            for (AppCartEntity cartEntity : cartList) {
                // 课程id
                String objId = String.valueOf(cartEntity.getId());
                // 判断用户是否已经购买了该课程
                Map<String, Object> isCollect = xryUserCollectService.isCollectByUserIdAndObjId(objId, getAppUserId());
                Integer isBuy = 0;
                if (null != isCollect) {
                    isBuy = 1;
                }
                
                cartList.add(cartEntity);
            }
        }
        map.put("cartList", cartList);
        return Result.ok(map);
    }

}
