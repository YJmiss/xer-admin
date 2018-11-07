package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XeyCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XeyCourseCatalogEntity;
import com.oservice.admin.modules.sys.service.XeyCourserCatService;
import com.oservice.admin.modules.sys.service.XeyCourserCatalogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * 课程目录表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xey/course/catalog")
public class XeyCourseCatalogController extends AbstractController {
    @Resource
    private XeyCourserCatalogService xeyCourseCatalogService;

    /**
     * 查询课程目录列表
     * @param params
     * @return
     */
    @SysLog("查询课程目录列表")
    @GetMapping("/list")
    @RequiresPermissions("xey:course:catalog:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xeyCourseCatalogService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程目录
     * @param courseCatalog
     * @return
     */
    @SysLog("保存课程目录")
    @PostMapping("/save")
    @RequiresPermissions("xey:course:catalog:save")
    public Result save(@RequestBody XeyCourseCatalogEntity courseCatalog){
        ValidatorUtils.validateEntity(courseCatalog, AddGroup.class);
        xeyCourseCatalogService.save(courseCatalog);
        return Result.ok();
    }

    /**
     * 课程目录信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xey:course:catalog:info")
    public Result info(@PathVariable("id") Long id){
        XeyCourseCatalogEntity courseCatalog = xeyCourseCatalogService.queryById(id);
        return Result.ok().put("courseCatalog", courseCatalog);
    }

    /**
     * 修改课程目录
     * @param courseCatalog
     * @return
     */
    @SysLog("修改课程目录")
    @PostMapping("/update")
    @RequiresPermissions("xey:course:catalog:update")
    public Result update(@RequestBody XeyCourseCatalogEntity courseCatalog){
        ValidatorUtils.validateEntity(courseCatalog, UpdateGroup.class);
        xeyCourseCatalogService.update(courseCatalog);
        return Result.ok();
    }

    /**
     * 删除课程目录
     * @param ids
     * @return
     */
    @SysLog("删除课程目录")
    @PostMapping("/delete")
    @RequiresPermissions("xey:course:catalog:delete")
    public Result delete(@RequestBody Long[] ids){
        xeyCourseCatalogService.deleteBatch(ids);
        return Result.ok();
    }
}
