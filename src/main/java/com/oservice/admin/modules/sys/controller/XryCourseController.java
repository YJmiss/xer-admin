package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import com.oservice.admin.modules.sys.service.XryCourseDescService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/course")
public class XryCourseController extends AbstractController {
    @Resource
    private XryCourseService xryCourserService;
    @Resource
    private XryCourseCatalogService xryCourseCatalogService;

    /**
     * 查询课程列表
     * @param params
     * @return
     */
    @SysLog("查询课程列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:course:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourserService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程
     * @param course
     * @return
     */
    @SysLog("保存课程")
    @PostMapping("/save")
    @RequiresPermissions("xry:course:save")
    public Result save(@RequestBody XryCourseEntity course){
        ValidatorUtils.validateEntity(course, AddGroup.class);
        xryCourserService.save(course);
        return Result.ok();
    }

    /**
     * 课程信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:course:info")
    public Result info(@PathVariable("id") Long id){
        XryCourseEntity course = xryCourserService.queryById(id);
        return Result.ok().put("course", course);
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @SysLog("修改课程")
    @PostMapping("/update")
    @RequiresPermissions("xry:course:update")
    public Result update(@RequestBody XryCourseEntity course){
        ValidatorUtils.validateEntity(course, UpdateGroup.class);
        xryCourserService.update(course);
        return Result.ok();
    }
    /**
     * 删除课程
     * @param ids
     * @return
     */
    @SysLog("删除课程")
    @PostMapping("/delete")
    @RequiresPermissions("xry:course:delete")
    public Result delete(@RequestBody Long[] ids){
        // 删除课程，同事删除课程对应的课程描述和课程目录
        for (Long id:ids) {
            XryCourseCatalogEntity xryCourseCatalogEntity = xryCourserService.queryCourseCatalogByCourseId(id);
            if (null != xryCourseCatalogEntity) {
                return Result.error("请先删除该课程下面的课程目录");
            }
            XryCourseDescEntity xryCourseDescEntity = xryCourserService.queryCourseDescById(id);
            if (null != xryCourseDescEntity) {
                return Result.error("请先删除该课程下面的课程描述");
            }
        }
        xryCourserService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造课程树
     * @return
     */
    @GetMapping("/treeCourse")
    @RequiresPermissions("xry:course:treeCourse")
    public Result treeCourse(){
        List<XryCourseEntity> courseList = xryCourserService.treeCourse();
        return Result.ok().put("courseList", courseList);
    }
}
