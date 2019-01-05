package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatalogService;
import com.oservice.admin.modules.sys.service.XryCourseService;
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
    @Resource
    private XryCourseCatalogService xryCourseCatalogService;
    @Resource
    private XryCourseService xryCourseService;

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
        // 修改视频（或者目录）之前要先判断与之关联的课程有没有上架（如果是上架则不能修改）
        XryCourseEntity course = xryCourseService.selectById(courseCatalog.getCourseid());
        if (3 == course.getStatus() || 4 == course.getStatus()) {
            return Result.error(1, "目录所属课程“" + course.getTitle() + "”已通过审核，不能修改该目录，请先下架该课程");
        } else {
            // 目录修改后重置为未审核
            xryCourseCatalogService.update(courseCatalog);
        }
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
        for (Long id : ids) {
            // 1、删除目录之前判断目录是否存在视频
            List<XryVideoEntity> videoList = xryCourseCatalogService.listVideoByCatalogId(id);
            if (videoList.size() > 0) {
                return Result.error(1, "该目录下有视频信息，不能删除该目录，请先删除该目录下的视频");
            } else {
                // 2、删除目录之前要先判断与之关联的课程有没有上架
                XryCourseCatalogEntity courseCatalog = xryCourseCatalogService.selectById(id);
                XryCourseEntity course = xryCourseService.selectById(courseCatalog.getCourseid());
                if (4 == course.getStatus()) {
                    return Result.error(1, "目录所属课程“" + course.getTitle() + "”已上架，不能删除该目录，请先下架该课程");
                } else {
                    xryCourseCatalogService.deleteBatch(ids);
                }
            }
        }
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
    
}
