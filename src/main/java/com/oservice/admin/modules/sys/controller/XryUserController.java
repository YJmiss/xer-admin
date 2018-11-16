package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * app用户表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/user")
public class XryUserController extends AbstractController {
    @Resource
    private XryUserService xryUserService;

    /**
     * 查询app用户列表
     * @param params
     * @return
     */
    @SysLog("查询app用户列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryUserService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存app用户
     * @param user
     * @return
     */
    @SysLog("保存app用户")
    @PostMapping("/save")
    @RequiresPermissions("xry:user:save")
    public Result save(@RequestBody XryUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);
        xryUserService.save(user);
        return Result.ok();
    }

    /**
     * app用户信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:user:info")
    public Result info(@PathVariable("id") Long id) {
        XryUserEntity user = xryUserService.queryById(id);
        return Result.ok().put("user", user);
    }

    /**
     * 修改app用户
     * @param user
     * @return
     */
    @SysLog("修改app用户")
    @PostMapping("/update")
    @RequiresPermissions("xry:user:update")
    public Result update(@RequestBody XryUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);
        xryUserService.update(user);
        return Result.ok();
    }

    /**
     * 删除app用户
     * @param ids
     * @return
     */
    @SysLog("删除app用户")
    @PostMapping("/delete")
    @RequiresPermissions("xry:user:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryUserService.deleteBatch(ids);
        return Result.ok();
    }
}
