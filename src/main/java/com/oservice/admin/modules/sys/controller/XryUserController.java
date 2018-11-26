package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app用户表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/user")
public class XryUserController extends AbstractController {
    /** 讲师<->普通用户切换 */
    final static Integer TEACHER_TO_NORMAL = 0;
    /** 普通用户<->讲师切换 */
    final static Integer NORMAL_TO_TEACHER = 1;
    @Resource
    private XryUserService xryUserService;

    /**
     * 查询app用户列表
     * @param params
     * @return
     */
    @SysLog("查询app用户列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryUserService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 删除app用户
     * @param ids
     * @return
     */
    @SysLog("删除app用户")
    @PostMapping("/delete")
    @RequiresPermissions("xry:user:delete")
    public Result delete(@RequestBody String[] ids) {
        xryUserService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造讲师树
     * @return
     */
    @SysLog("讲师树")
    @GetMapping("/treeUser")
    @RequiresPermissions("xry:user:treeUser")
    public Result treeCourse() {
        List<XryUserEntity> userList = xryUserService.treeUser();
        return Result.ok().put("userList", userList);
    }

    /**
     * 讲师<->普通用户切换
     * @param ids
     * @return
     */
    @SysLog("讲师<->普通用户切换")
    @PostMapping("/updateUserRole")
    @RequiresPermissions("xry:user:updateUserRole")
    public Result updateUserRole(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",TEACHER_TO_NORMAL);
        xryUserService.updateUserRole(params);
        return Result.ok();
    }

    /**
     * 普通用户<->讲师切换
     * @param ids
     * @return
     */
    @SysLog("普通用户<->讲师切换")
    @PostMapping("/updateUserRoleTeacher")
    @RequiresPermissions("xry:user:updateUserRoleTeacher")
    public Result updateUserRoleTeacher(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",NORMAL_TO_TEACHER);
        xryUserService.updateUserRole(params);
        return Result.ok();
    }
    
}
