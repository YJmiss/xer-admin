package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程目录表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/course/catalog")
public class XryCourseCatalogController extends AbstractController {
    @Resource
    private XryCourseCatalogService xryCourseCatalogService;

    /**
     * 查询课程目录列表
     * @param params
     * @return
     */
    @SysLog("查询课程目录列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:course:catalog:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourseCatalogService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程目录
     * @param courseCatalog
     * @return
     */
    @SysLog("保存课程目录")
    @PostMapping("/save")
    @RequiresPermissions("xry:course:catalog:save")
    public Result save(@RequestBody XryCourseCatalogEntity courseCatalog){
        ValidatorUtils.validateEntity(courseCatalog, AddGroup.class);
        xryCourseCatalogService.save(courseCatalog);
        return Result.ok();
    }

    /**
     * 课程目录信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:course:catalog:info")
    public Result info(@PathVariable("id") Long id){
        XryCourseCatalogEntity courseCatalog = xryCourseCatalogService.queryById(id);
        return Result.ok().put("courseCatalog", courseCatalog);
    }

    /**
     * 修改课程目录
     * @param courseCatalog
     * @return
     */
    @SysLog("修改课程目录")
    @PostMapping("/update")
    @RequiresPermissions("xry:course:catalog:update")
    public Result update(@RequestBody XryCourseCatalogEntity courseCatalog){
        ValidatorUtils.validateEntity(courseCatalog, UpdateGroup.class);
        xryCourseCatalogService.update(courseCatalog);
        return Result.ok();
    }

    /**
     * 删除课程目录
     * @param ids
     * @return
     */
    @SysLog("删除课程目录")
    @PostMapping("/delete")
    @RequiresPermissions("xry:course:catalog:delete")
    public Result delete(@RequestBody Long[] ids){
        xryCourseCatalogService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 课程树(添加、修改菜单)
     */
    @GetMapping("/select")
    @RequiresPermissions("xry:course:catalog:select")
    public Result select(){
        //查询列表数据
        List<XryCourseCatalogEntity> courseList = xryCourseCatalogService.treeCourseList();
        return Result.ok().put("courseList", courseList);
    }

    /**
     * 课程目录树(添加、修改菜单)
     */
    @GetMapping("/treeCourseCatalog")
    @RequiresPermissions("xry:course:catalog:treeCourseCatalog")
    public Result treeCourseList(){
        //查询列表数据
        List<XryCourseCatalogEntity> courseCatalogList = xryCourseCatalogService.treeCourseCatalogList();
        return Result.ok().put("courseCatalogList", courseCatalogList);
    }

}
