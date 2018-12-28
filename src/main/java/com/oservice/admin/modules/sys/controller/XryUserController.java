package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
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
    @Resource
    private XryTeacherService xryTeacherService;

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
     * 构造用户树
     * @return
     */
    @SysLog("用户树")
    @GetMapping("/treeUser")
    @RequiresPermissions("xry:user:treeUser")
    public Result treeUser() {
        List<XryUserEntity> userList = xryUserService.treeUser();
        return Result.ok().put("userList", userList);
    }

    /**
     * 普通用户<->讲师切换
     * @param ids
     * @return
     */
    @SysLog("普通用户<->讲师切换")
    @PostMapping("/updateUserRoleToTeacher")
    @RequiresPermissions("xry:user:updateUserRoleToTeacher")
    public Result updateUserRoleToTeacher(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        params.put("role",NORMAL_TO_TEACHER);
        xryUserService.updateUserRole(params);
        // 把用户置为讲师后，向xry_teacher中加入该对象
        for (String id : ids) {
            XryTeacherEntity teacherEntity = new XryTeacherEntity();
            teacherEntity.setStatus(1);
            teacherEntity.setCreated(new Date());
            teacherEntity.setUserId(id);
            xryTeacherService.insert(teacherEntity);
        }
        return Result.ok();
    }

    /**
     * 讲师切换<->普通用户
     * @param ids
     * @return
     */
    @SysLog("讲师切换<->普通用户")
    @PostMapping("/updateTeacherRoleToUser")
    @RequiresPermissions("xry:user:updateTeacherRoleToUser")
    public Result updateTeacherRoleToUser(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        params.put("role",TEACHER_TO_NORMAL);
        xryUserService.updateUserRole(params);
        // 把讲师置为普通用户后，向xry_teacher中删除该讲师
        xryTeacherService.deleteBatch(ids);
        return Result.ok();
    }

    
}
