package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryPermissionEntity;
import com.oservice.admin.modules.sys.service.XryPermissionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * app权限表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/permission")
public class XryPermissionController extends AbstractController {
    @Resource
    private XryPermissionService xryPermissionService;

    /**
     * 查询app权限列表
     * @param params
     * @return
     */
    @SysLog("查询app权限列表")
    @GetMapping("/list")
    /*@RequiresPermissions("xry:permission:list")*/
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryPermissionService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存app权限
     * @param permission
     * @return
     */
    @SysLog("保存app权限")
    @PostMapping("/save")
    /*@RequiresPermission("xry:permission:save")*/
    public Result save(@RequestBody XryPermissionEntity permission){
        ValidatorUtils.validateEntity(permission, AddGroup.class);
        xryPermissionService.save(permission);
        return Result.ok();
    }

    /**
     * app权限信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    /*@RequiresPermissions("xry:permission:info")*/
    public Result info(@PathVariable("id") Long id){
        XryPermissionEntity permission = xryPermissionService.queryById(id);
        return Result.ok().put("permission", permission);
    }

    /**
     * 修改app权限
     * @param permission
     * @return
     */
    @SysLog("修改app权限")
    @PostMapping("/update")
    /*@RequiresPermissions("xry:permission:update")*/
    public Result update(@RequestBody XryPermissionEntity permission){
        ValidatorUtils.validateEntity(permission, UpdateGroup.class);
        xryPermissionService.update(permission);
        return Result.ok();
    }

    /**
     * 删除app权限
     * @param ids
     * @return
     */
    @SysLog("删除app权限")
    @PostMapping("/delete")
   /* @RequiresPermissions("xry:permission:delete")*/
    public Result delete(@RequestBody Long[] ids){
        xryPermissionService.deleteBatch(ids);
        return Result.ok();
    }
}
