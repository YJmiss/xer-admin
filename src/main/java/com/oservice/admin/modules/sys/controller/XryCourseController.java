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
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryRecordService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    /** 课程审核通过常量 */
    final static Integer COURSE_EXAMINE_PASS = 3;
    /** 课程审核驳回常量 */
    final static Integer COURSE_EXAMINE_REJECT = 4;
    /** 课程审核的标识符 */
    final static Integer COURSE_EXAMINE_FLAG = 1;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private XryRecordService xryRecordService;


    /**
     * 查询课程列表
     * @param params
     * @return
     */
    @SysLog("查询课程列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:course:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourseService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 查询课程审核列表
     * @param params
     * @return
     */
    @SysLog("查询课程审核列表")
    @GetMapping("/examineList")
    @RequiresPermissions("xry:course:examineList")
    public Result examineList(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourseService.examineList(params);
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
        xryCourseService.save(course);
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
        XryCourseEntity course = xryCourseService.queryById(id);
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
        xryCourseService.update(course);
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
            XryCourseCatalogEntity xryCourseCatalogEntity = xryCourseService.queryCourseCatalogByCourseId(id);
            if (null != xryCourseCatalogEntity) {
                return Result.error("请先删除该课程下的课程目录");
            }
            XryCourseDescEntity xryCourseDescEntity = xryCourseService.queryCourseDescById(id);
            if (null != xryCourseDescEntity) {
                return Result.error("请先删除该课程下的课程描述");
            }
        }
        xryCourseService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造课程树
     *
     * @return
     */
    @SysLog("课程树")
    @GetMapping("/treeCourse")
    @RequiresPermissions("xry:course:treeCourse")
    public Result treeCourse() {
        List<XryCourseEntity> courseList = xryCourseService.treeCourse();
        return Result.ok().put("courseList", courseList);
    }

    /**
     * 课程上架：5
     * @param ids
     * @return
     */
    @SysLog("课程上架")
    @PostMapping("/addToCourse")
    @RequiresPermissions("xry:course:add:to:course")
    public Result addToCourse(@RequestBody Long[] ids) {
        // 课程上架之前先判断课程是否已经审核 审核状态：1、2、4
        for (Long id:ids) {
            XryCourseEntity xryCourseEntity = xryCourseService.queryById(id);
            if (1 == xryCourseEntity.getStatus() || 2 == xryCourseEntity.getStatus() || 4 == xryCourseEntity.getStatus()) {
                return Result.error("所选记录中有未审核的课程，请先审核通过该课程再进行此操作");
            }
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",ADD_TO_COURSE);
        xryCourseService.updateCourseStatus(params);
        return Result.ok();
    }

    /**
     * 课程下架：6
     * @param ids
     * @return
     */
    @SysLog("课程下架")
    @PostMapping("/delFromCourse")
    @RequiresPermissions("xry:course:del:from:course")
    public Result delFromCourse(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",DEL_FROM_COURSE);
        xryCourseService.updateCourseStatus(params);
        return Result.ok();
    }

    /**
     * 审核系统->课程审核：3
     * @param ids
     * @return
     */
    @SysLog("审核系统->课程审核")
    @PostMapping("/examinePass")
    @RequiresPermissions("xry:course:examine:pass")
    public Result examinePass(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",COURSE_EXAMINE_PASS);
        xryCourseService.updateCourseStatus(params);
        // 记录课程审核事件
        params.put("userId",getUserId());
        params.put("type",COURSE_EXAMINE_FLAG);
        /*xryRecordService.recordCourseExamine(params);*/
        return Result.ok("审核通过");
    }

    @SysLog("审核系统->审核驳回")
    @PostMapping("/examineReject")
    @RequiresPermissions("xry:course:examine:reject")
    public Result examineReject(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",COURSE_EXAMINE_REJECT);
        xryCourseService.updateCourseStatus(params);
        // 记录课程审核事件
        params.put("userId",getUserId());
        params.put("type",COURSE_EXAMINE_FLAG);
        /*xryRecordService.recordCourseExamine(params);*/
        return Result.ok("审核驳回");
    }
    
}
