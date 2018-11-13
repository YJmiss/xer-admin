package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * 课程类目表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/course/cat")
public class XryCourseCatController extends AbstractController {
    @Resource
    private XryCourseCatService xryCourseCatService;

    /**
     * 查询课程列表
     * @param params
     * @return
     */
    @SysLog("查询课程类目列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:course:cat:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourseCatService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程类目
     * @param courseCat
     * @return
     */
    @SysLog("保存课程类目")
    @PostMapping("/save")
    @RequiresPermissions("xry:course:cat:save")
    public Result save(@RequestBody XryCourseCatEntity courseCat){
        ValidatorUtils.validateEntity(courseCat, AddGroup.class);
        xryCourseCatService.save(courseCat);
        return Result.ok();
    }

    /**
     * 课程类目信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:course:cat:info")
    public Result info(@PathVariable("id") Long id){
        XryCourseCatEntity courseCat = xryCourseCatService.queryById(id);
        System.out.println(courseCat);
        return Result.ok().put("courseCat", courseCat);
    }

    /**
     * 修改课程类目
     * @param courseCat
     * @return
     */
    @SysLog("修改课程类目")
    @PostMapping("/update")
    @RequiresPermissions("xry:course:cat:update")
    public Result update(@RequestBody XryCourseCatEntity courseCat){
        ValidatorUtils.validateEntity(courseCat, UpdateGroup.class);
        xryCourseCatService.update(courseCat);
        return Result.ok();
    }

    /**
     * 删除课程类目
     * @param ids
     * @return
     */
    @SysLog("删除课程类目")
    @PostMapping("/delete")
    @RequiresPermissions("xry:course:cat:delete")
    public Result delete(@RequestBody Long[] ids){
        xryCourseCatService.deleteBatch(ids);
        return Result.ok();
    }
}
