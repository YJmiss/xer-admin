package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.service.XryCourseDescService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * 课程描述表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xey/course/desc")
public class XryCourseDescController extends AbstractController {
    @Resource
    private XryCourseDescService xeyCourseDescService;

    /**
     * 查询课程列表
     * @param params
     * @return
     */
    @SysLog("查询课程描述列表")
    @GetMapping("/list")
    @RequiresPermissions("xey:course:desc:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xeyCourseDescService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程描述
     * @param courseDesc
     * @return
     */
    @SysLog("保存课程描述")
    @PostMapping("/save")
    @RequiresPermissions("xey:course:desc:save")
    public Result save(@RequestBody XryCourseDescEntity courseDesc){
        ValidatorUtils.validateEntity(courseDesc, AddGroup.class);
        xeyCourseDescService.save(courseDesc);
        return Result.ok();
    }

    /**
     * 课程描述信息
     * @param courseId
     * @return
     */
    @GetMapping("/info/{courseId}")
    @RequiresPermissions("xey:course:desc:info")
    public Result info(@PathVariable("courseId") Long courseId){
        XryCourseDescEntity courseDesc = xeyCourseDescService.queryById(courseId);
        return Result.ok().put("courseDesc", courseDesc);
    }

    /**
     * 修改课程描述
     * @param courseDesc
     * @return
     */
    @SysLog("修改课程描述")
    @PostMapping("/update")
    @RequiresPermissions("xey:course:desc:update")
    public Result update(@RequestBody XryCourseDescEntity courseDesc){
        ValidatorUtils.validateEntity(courseDesc, UpdateGroup.class);
        xeyCourseDescService.update(courseDesc);
        return Result.ok();
    }

    /**
     * 删除课程描述
     * @param courseIds
     * @return
     */
    @SysLog("删除课程描述")
    @PostMapping("/delete")
    @RequiresPermissions("xey:course:desc:delete")
    public Result delete(@RequestBody Long[] courseIds){
        xeyCourseDescService.deleteBatch(courseIds);
        return Result.ok();
    }
}
