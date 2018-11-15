package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryRoleEntity;
import com.oservice.admin.modules.sys.service.XryRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * app角色表的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/role")
public class XryRoleController extends AbstractController {
    @Resource
    private XryRoleService xryRoleService;

    /**
     * 查询app用户角色列表
     *
     * @param params
     * @return
     */
    @SysLog("查询app用户角色列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:role:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryRoleService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存app用户角色
     *
     * @param role
     * @return
     */
    @SysLog("保存app用户角色")
    @PostMapping("/save")
    @RequiresPermissions("xry:role:save")
    public Result save(@RequestBody XryRoleEntity role) {
        ValidatorUtils.validateEntity(role, AddGroup.class);
        xryRoleService.save(role);
        return Result.ok();
    }

    /**
     * app用户角色信息
     *
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:role:info")
    public Result info(@PathVariable("id") Long id) {
        XryRoleEntity role = xryRoleService.queryById(id);
        return Result.ok().put("role", role);
    }

    /**
     * 修改app用户角色
     *
     * @param role
     * @return
     */
    @SysLog("修改app用户角色")
    @PostMapping("/update")
    @RequiresPermissions("xry:role:update")
    public Result update(@RequestBody XryRoleEntity role) {
        ValidatorUtils.validateEntity(role, UpdateGroup.class);
        xryRoleService.update(role);
        return Result.ok();
    }

    /**
     * 删除app用户角色
     *
     * @param ids
     * @return
     */
    @SysLog("删除app用户角色")
    @PostMapping("/delete")
    @RequiresPermissions("xry:role:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryRoleService.deleteBatch(ids);
        return Result.ok();
    }
}