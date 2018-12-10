package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.modules.app.service.SolrJService;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryOrganizationEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryOrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * 机构表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/organization")
public class XryOrganizationController extends AbstractController {
    @Resource
    private XryOrganizationService xryOrganizationService;
    /**
     * 搜索服务
     */
    @Autowired
    private SolrJService solrJService;
    /**
     * 查询机构列表
     * @param params
     * @return
     */
    @SysLog("查询机构列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:organization:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryOrganizationService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存机构
     * @param org
     * @return
     */
    @SysLog("保存机构")
    @PostMapping("/save")
    @RequiresPermissions("xry:organization:save")
    public Result save(@RequestBody XryOrganizationEntity org){
        ValidatorUtils.validateEntity(org, AddGroup.class);
        xryOrganizationService.save(org);
        return Result.ok();
    }

    /**
     * 机构信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:organization:info")
    public Result info(@PathVariable("id") Long id){
        XryOrganizationEntity org = xryOrganizationService.queryById(id);
        return Result.ok().put("org", org);
    }

    /**
     * 删除机构
     * @param ids
     * @return
     */
    @SysLog("删除机构")
    @PostMapping("/delete")
    @RequiresPermissions("xry:organization:delete")
    public Result delete(@RequestBody Long[] ids){

        xryOrganizationService.deleteBatch(ids);
        return Result.ok();
    }

}
