package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    /** 课程类目启用标志 */
    final static Integer COURSE_CAT_TO_USE = 1;
    /** 课程类目禁用标志 */
    final static Integer COURSE_CAT_TO_DISABLE = 2;
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
        for (Long id: ids) {
            // 删除类目时查询该类目下是否有课程
            List<XryCourseEntity> courseList = xryCourseCatService.listCourseByCourseCatalogId(id);
            if (courseList.size() > 0) {
                return Result.error("请先删除该类目下的课程");
            }
            // 删除类目时查询该类目下是否有子类目
            List<XryCourseCatEntity> courseCatList = xryCourseCatService.isParentCourseCatalogById(id);
            if (courseCatList.size() > 0) {
                return Result.error("请先删除该类目下的子类目");
            }
        }
        xryCourseCatService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 课程类目树(添加、修改菜单)
     */
    @GetMapping("/treeCourseCat")
    @RequiresPermissions("xry:course:cat:treeCourseCat")
    public Result treeCourseCat() {
        List<XryCourseCatEntity> courseCatList = xryCourseCatService.treeCourseCat();
        return Result.ok().put("courseCatList", courseCatList);
    }
    
    /**
     * 启用类目：1
     * @param ids
     * @return
     */
    @SysLog("启用类目")
    @PostMapping("/toUse")
    @RequiresPermissions("xry:course:cat:toUse")
    public Result catToUse(@RequestBody Long[] ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids",ids);
        params.put("status",COURSE_CAT_TO_USE);
        xryCourseCatService.updateCourseCatStatus(params);
        return Result.ok();
    }
    
    /**
     * 禁用类目：2
     * @param ids
     * @return
     */
    @SysLog("禁用类目")
    @PostMapping("/toDisable")
    @RequiresPermissions("xry:course:cat:toDisable")
    public Result catToDisable(@RequestBody Long[] ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids",ids);
        params.put("status",COURSE_CAT_TO_DISABLE);
        xryCourseCatService.updateCourseCatStatus(params);
        return Result.ok();
    }

}
