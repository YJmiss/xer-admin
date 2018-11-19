package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.*;
import java.util.HashMap;
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
    /** 课程上架标识符常量 */
    final static Integer ADD_TO_COURSE = 6;
    /** 课程下架标识符常量 */
    final static Integer DEL_FROM_COURSE = 5;
    @Resource
    private XryCourseService xryCourserService;


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
        for (Long id : ids) {
            XryCourseCatalogEntity xryCourseCatalogEntity = xryCourserService.queryCourseCatalogByCourseId(id);
            if (null != xryCourseCatalogEntity) {
                return Result.error("请先删除该课程下的课程目录");
            }
            XryCourseDescEntity xryCourseDescEntity = xryCourserService.queryCourseDescById(id);
            if (null != xryCourseDescEntity) {
                return Result.error("请先删除该课程下的课程描述");
            }
        }
        xryCourserService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造课程树
     *
     * @return
     */
    @SysLog("删除课程")
    @GetMapping("/treeCourse")
    @RequiresPermissions("xry:course:treeCourse")
    public Result treeCourse() {
        List<XryCourseEntity> courseList = xryCourserService.treeCourse();
        return Result.ok().put("courseList", courseList);
    }

    /**
     * 课程上架
     * @param ids
     * @return
     */
    @SysLog("课程上架")
    @PostMapping("/addToCourse")
    @RequiresPermissions("xry:course:add:to:course")
    public Result addToCourse(@RequestBody Long[] ids) {
        // 课程上架之前先判断课程是否已经审核 审核状态：1、2、4
        for (Long id:ids) {
            XryCourseEntity xryCourseEntity = xryCourserService.queryById(id);
            if (1 == xryCourseEntity.getStatus() || 2 == xryCourseEntity.getStatus() || 4 == xryCourseEntity.getStatus()) {
                return Result.error("所选记录中有未审核的课程，请先审核通过该课程再进行此操作");
            }
        }

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",ADD_TO_COURSE);
        xryCourserService.updateCourseStatus(params);
        return Result.ok();
    }

    /**
     * 课程下架
     * @param ids
     * @return
     */
    @SysLog("课程下架")
    @PostMapping("/delFromCourse")
    @RequiresPermissions("xry:course:del:from:course")
    public Result delFromCourse(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap();
        params.put("ids",ids);
        params.put("flag",DEL_FROM_COURSE);
        xryCourserService.updateCourseStatus(params);
        return Result.ok();
    }

    /**
     * 审核系统->课程审核
     * @param ids
     * @return
     */
    @SysLog("审核系统->课程审核")
    @GetMapping("/examine")
    @RequiresPermissions("xry:course:examine")
    public Result examine(@RequestBody Long[] ids) {

        return Result.ok().put("courseList", "");
    }

}
