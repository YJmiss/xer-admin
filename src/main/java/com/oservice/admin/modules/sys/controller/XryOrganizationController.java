package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.service.SysUserTokenService;
import com.oservice.admin.modules.sys.service.XryOrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 机构表的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/organization")
public class XryOrganizationController extends AbstractController {
    @Resource
    private XryOrganizationService xryOrganizationService;
    @Resource
    private SysUserTokenService sysUserTokenService;

    /**
     * 查询机构列表
     * @param params
     * @return
     */
    @SysLog("查询机构列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:organization:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryOrganizationService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 机构信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:organization:info")
    public Result info(@PathVariable("id") Long id) {
        Map<String, Object> org = xryOrganizationService.queryById(id);
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
    public Result delete(@RequestBody Long[] ids) {
        xryOrganizationService.deleteBatch(ids);
        return Result.ok();
    }


    /**
     * app端保存机构信息
     *
     * @param params
     * @return
     */
    @SysLog("app端保存机构信息")
    @PostMapping("/appSave")
    @RequiresPermissions("xry:organization:appSave")
    public Result appSave(@RequestParam String[] params) {
        //ValidatorUtils.validateEntity(org, AddGroup.class);
        xryOrganizationService.save(params);
        return Result.ok();
    }

    /**
     * app查询机构列表
     *
     * @param token
     * @return
     */
    @SysLog("app查询机构列表")
    @GetMapping("/appList")
    @RequiresPermissions("xry:organization:appList")
    public Result appList(@RequestParam String token) {
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(token);
        if (null == tokenEntity) {
            return Result.error(1, "token获取失败或已失效");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId", tokenEntity.getUserId());
        List<Map<String, Object>> orgList = xryOrganizationService.listByUserId(params);
        return Result.ok().put("orgList", orgList);
    }

}
