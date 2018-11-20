package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.service.XryContentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * 广告内容表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/content")
public class XryContentController extends AbstractController {
    @Resource
    private XryContentService xryContentService;

    /**
     * 查询广告内容列表
     * @param params
     * @return
     */
    @SysLog("查询广告内容列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:content:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryContentService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存广告内容
     * @param content
     * @return
     */
    @SysLog("保存广告内容")
    @PostMapping("/save")
    @RequiresPermissions("xry:content:save")
    public Result save(@RequestBody XryContentEntity content) {
        ValidatorUtils.validateEntity(content, AddGroup.class);
        content.setCreated(new Date());
        content.setUpdated(new Date());
        xryContentService.save(content);
        return Result.ok();
    }

    /**
     * 广告内容信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:content:info")
    public Result info(@PathVariable("id") Long id) {
        XryContentEntity content = xryContentService.queryById(id);
        return Result.ok().put("content", content);
    }

    /**
     * 修改广告内容
     * @param content
     * @return
     */
    @SysLog("修改广告内容")
    @PostMapping("/update")
    @RequiresPermissions("xry:content:update")
    public Result update(@RequestBody XryContentEntity content) {
        ValidatorUtils.validateEntity(content, UpdateGroup.class);
        xryContentService.update(content);
        return Result.ok();
    }

    /**
     * 删除广告内容
     * @param ids
     * @return
     */
    @SysLog("删除广告内容")
    @PostMapping("/delete")
    @RequiresPermissions("xry:content:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryContentService.deleteBatch(ids);
        return Result.ok();
    }

}
