package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.modules.app.service.SolrJService;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * 讲师表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/teacher")
public class XryTeacherController extends AbstractController {
    @Resource
    private XryTeacherService xryTeacherService;
    /**
     * 搜索服务
     */
    @Autowired
    private SolrJService solrJService;
    /**
     * 查询讲师列表
     * @param params
     * @return
     */
    @SysLog("查询讲师列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:teacher:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryTeacherService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存讲师
     * @param teacher
     * @return
     */
    @SysLog("保存讲师")
    @PostMapping("/save")
    @RequiresPermissions("xry:teacher:save")
    public Result save(@RequestBody XryTeacherEntity teacher){
        ValidatorUtils.validateEntity(teacher, AddGroup.class);
        xryTeacherService.save(teacher);
        return Result.ok();
    }

    /**
     * 讲师信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:teacher:info")
    public Result info(@PathVariable("id") Long id){
        Map<String, Object> teacher = xryTeacherService.queryById(id);
        return Result.ok().put("teacher", teacher);
    }

    /**
     * 删除讲师
     * @param ids
     * @return
     */
    @SysLog("删除讲师")
    @PostMapping("/delete")
    @RequiresPermissions("xry:teacher:delete")
    public Result delete(@RequestBody Long[] ids){
        xryTeacherService.deleteBatch(ids);
        return Result.ok();
    }

    
}
