package com.oservice.admin.modules.wechat.common.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.modules.wechat.common.entity.WeixinAuthorize;
import com.oservice.admin.modules.wechat.common.service.WeixinAuthorizeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @program: oservice
 * @description: 微信配置授权信息
 * @author: YJmiss
 * @create: 2018-12-12 15:46
 **/
@RestController
public class WeixinAuthorizeController {
    @Resource
    WeixinAuthorizeService authorizeService;

    /**
     * @Description: 保存公众号配置用户信息
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @PostMapping("/sys/weChatAuthConfig")
    public Result addAuthorize(@RequestBody WeixinAuthorize weixinAuthorize) {
        weixinAuthorize.setGzhType(2);
        weixinAuthorize.setId(UUIDUtils.getUUID());
        Boolean br = authorizeService.addAuthorize(weixinAuthorize);
        if (br) {
            return Result.ok();
        }
        return Result.error(500, "配置信息保存失败！！");
    }

    /**
     * @Description: 列表所有配置信息
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @GetMapping("/sys/weChatAuthConfig/list")
    public Result getAuthorizeList() {
        Map<String, Object> map = new HashMap<>();
        List<WeixinAuthorize> authorizes = authorizeService.getAuthorizeList();
        map.put("authorizes", authorizes);
        return Result.ok(map);
    }

    /**
     * @Description: 移除选择配置信息
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @GetMapping("/sys/weChatAuthConfig/deleteAuthorizeById")
    public Result deleteAuthorizeById(String id) {
        Boolean br = authorizeService.deleteAuthorizeByid(id);
        if (br) {
            return Result.ok();
        }
        return Result.error(500, "移除用户配置信息失败！！");
    }

    /**
     * @Description: 配置用户信息启动and停用
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @GetMapping("/sys/weChatAuthConfig/updateAuthorizeById")
    public Result updateAuthorizeById(String id) {
        WeixinAuthorize authorize = authorizeService.getAuthorizeByid(id);
        if (authorize == null) {
            return Result.error(500, "系统错误，请联系管理员....");
        }
        int a = authorize.getGzhType();
        if (a == 1) {
            authorize.setGzhType(2);
        } else {
            authorize.setGzhType(1);
        }
        Boolean br = authorizeService.updataAuthorizeById(authorize);
        if (br) {
            return Result.ok();
        } else {
            return Result.error(500, "系统错误!请联系管理员...");
        }
    }

}
