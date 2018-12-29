package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import com.oservice.admin.modules.sys.service.SysUserTokenService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
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
    /** 讲师推荐 */
    final static Integer RECOMMEND_TEACHER= 1;
    /**  取消推荐 */
    final static Integer CANCEL_RECOMMEND = 0;
    
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private SysUserTokenService sysUserTokenService;

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
     * 保存讲师资料
     * @param xryTeacherEntity
     * @return
     */
    @SysLog("保存讲师资料")
    @PostMapping("/updateTeacher")
    @RequiresPermissions("xry:teacher:updateTeacher")
    public Result updateTeacher(@RequestBody XryTeacherEntity xryTeacherEntity){
        ValidatorUtils.validateEntity(xryTeacherEntity, AddGroup.class);
        xryTeacherService.updateById(xryTeacherEntity);
        return Result.ok();
    }

    /**
     * 构造讲师树
     * @return
     */
    @SysLog("讲师树")
    @GetMapping("/treeTeacher")
    @RequiresPermissions("xry:teacher:treeTeacher")
    public Result treeTeacher() {
        List<XryTeacherEntity> teacherList = xryTeacherService.treeTeacher();
        return Result.ok().put("teacherList", teacherList);
    }

    /**
     * 讲师信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:teacher:info")
    public Result info(@PathVariable("id") Long id) {
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
    public Result delete(@RequestBody String[] ids){
        xryTeacherService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 讲师推荐
     * @param ids
     * @return
     */
    @SysLog("讲师推荐")
    @PostMapping("/recommendTeacher")
    @RequiresPermissions("xry:teacher:recommendTeacher")
    public Result recommendTeacher(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("recommend",RECOMMEND_TEACHER);
        xryTeacherService.updateTeacherRecommend(params);
        return Result.ok();
    }

    /**
     * 取消推荐
     * @param ids
     * @return
     */
    @SysLog("取消推荐")
    @PostMapping("/cancelRecommend")
    @RequiresPermissions("xry:teacher:cancelRecommend")
    public Result cancelRecommend(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("recommend",CANCEL_RECOMMEND);
        xryTeacherService.updateTeacherRecommend(params);
        return Result.ok();
    }
    
}
