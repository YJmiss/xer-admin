package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
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
@Api(description = "课程目录管理")
public class XryCourseCatalogController extends AbstractController {
    /** 目录上架标识符常量 */
   final static Integer ADD_TO_COURSE_CATALOG = 6;
   /** 目录下架标识符常量 */
   final static Integer DEL_FROM_COURSE_CATALOG = 5;
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
     * 构造目录树
     *
     * @return
     */
    @GetMapping("/treeCourseCatalog")
    @RequiresPermissions("xry:course:catalog:treeCourseCatalog")
    public Result treeCourseCatalog( Long courseId) {
        List<XryCourseCatalogEntity> courseCatalogList = xryCourseCatalogService.treeCourseCatalog(courseId);
        return Result.ok().put("courseCatalogList", courseCatalogList);
    }

    /**
     * 目录上架
     * @param ids
     * @return
     */
    @SysLog("目录上架")
    @PostMapping("/addToCourseCatalog")
    @RequiresPermissions("xry:course:add:to:course:catalog")
    public Result addToCourseCatalog(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",ADD_TO_COURSE_CATALOG);
        xryCourseCatalogService.updateCourseCatalogStatus(params);
        return Result.ok();
    }

    /**
     * 目录下架
     * @param ids
     * @return
     */
    @SysLog("目录下架")
    @PostMapping("/delFromCourseCatalog")
    @RequiresPermissions("xry:course:del:from:course:catalog")
    public Result delFromCourseCatalog(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap();
        params.put("ids",ids);
        params.put("flag",DEL_FROM_COURSE_CATALOG);
        xryCourseCatalogService.updateCourseCatalogStatus(params);
        return Result.ok();
    }
    
}
