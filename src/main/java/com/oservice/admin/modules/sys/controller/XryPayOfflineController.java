package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统用户
 * 线下支付表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/payOffline")
public class XryPayOfflineController extends AbstractController {
    @Resource
    private XryPayOfflineService xryPayOfflineService;

    /**
     * 查询线下支付列表
     * @param params
     * @return
     */
    @SysLog("查询线下支付列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:payOffline:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryPayOfflineService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 删除线下支付
     * @param ids
     * @return
     */
    @SysLog("删除线下支付")
    @PostMapping("/delete")
    @RequiresPermissions("xry:payOffline:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryPayOfflineService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 保存线下支付
     * @param xryPayOffline
     * @return
     */
    @SysLog("保存线下支付")
    @PostMapping("/save")
    @RequiresPermissions("xry:payOffline:save")
    public Result save(@RequestBody XryPayOfflineEntity xryPayOffline) {
        ValidatorUtils.validateEntity(xryPayOffline, AddGroup.class);
        xryPayOffline.setSuId(String.valueOf(getUser().getUserId()));
        xryPayOffline.setCreateTime(new Date());
        xryPayOfflineService.save(xryPayOffline);
        return Result.ok();
    }

    /**
     * 线下支付信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:payOffline:info")
    public Result info(@PathVariable("id") Long id){
        XryPayOfflineEntity xryPayOffline = xryPayOfflineService.queryById(id);
        return Result.ok().put("xryPayOffline", xryPayOffline);
    }

    /**
     * 修改线下支付
     * @param xryPayOffline
     * @return
     *
     */
    @SysLog("修改线下支付")
    @PostMapping("/update")
    @RequiresPermissions("xry:payOffline:update")
    public Result update(@RequestBody XryPayOfflineEntity xryPayOffline){
        ValidatorUtils.validateEntity(xryPayOffline, UpdateGroup.class);
        xryPayOffline.setSuId(String.valueOf(getUser().getUserId()));
        xryPayOfflineService.update(xryPayOffline);
        return Result.ok();
    }

}
