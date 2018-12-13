package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.modules.app.service.SolrJService;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.SysUserTokenService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryCourseTeacherUserService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
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
    /** 讲师关注的数据库标识符 */
    private static final Integer TEACHER_FOCUS_FLAG = 2;
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private XryCourseTeacherUserService xryCourseTeacherUserService;
    
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

    /**
     * 保存讲师认证信息
     * @param params
     * @return
     */
    @SysLog("保存讲师认证信息")
    @PostMapping("/appSave")
    @RequiresPermissions("xry:teacher:appSave")
    public Result appSave(@RequestParam String[] params){
        //ValidatorUtils.validateEntity(teacher, AddGroup.class);
        xryTeacherService.save(params);
        return Result.ok();
    }

    /**
     * app查询讲师列表
     * @param token
     * @return
     */
    @SysLog("app查询讲师列表")
    @GetMapping("/appList")
    @RequiresPermissions("xry:teacher:appList")
    public Result appList(@RequestParam String token) {
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(token);
        if (null == tokenEntity) {
            return Result.error(1,"token获取失败或已失效");
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId",tokenEntity.getUserId());
        List<Map<String,Object>> orgList = xryTeacherService.listByUserId(params);
        return Result.ok().put("orgList", orgList);
    }

    /**
     * app端讲师关注
     * @param token 用户的token
     * @param teacherId 讲师id
     * @param isSelect 是否关注
     * @return
     */
    @SysLog("app端讲师关注")
    @PostMapping("/appJoinCourseStudy")
    public Result appJoinCourseStudy(@RequestParam String token, String teacherId, boolean isSelect) {
        // 从token中获取登录人信息
        JSONObject tokenJSONObject = new JSONObject(token);
        String json = tokenJSONObject.getString("token");
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(json);
        if (null == tokenEntity) {
            return Result.error(1,"token已过期，请重新登录！");
        }
        // 把课程id和用户id加入到数据库表中
        Map<String,Object> params = new HashMap<>();
        params.put("userId",tokenEntity.getUserId());
        params.put("teacherId",teacherId);
        params.put("type",TEACHER_FOCUS_FLAG);
        Integer isSuccess = xryCourseTeacherUserService.saveTeacher(params);
        if (1 == isSuccess) {
            return Result.ok().put("1","讲师关注成功");
        } else {
            return Result.error(2,"讲师关注失败");
        }
    }

    /**
     * app端根据用户查询用户关注的讲师列表
     * @param token
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SysLog("app端根据用户查询用户关注的讲师列表")
    @GetMapping("/appPageListTeacherByUserId")
    public Result appPageListTeacherByUserId(@RequestParam String token, Integer pageNo, Integer pageSize) {
        // 从token中获取登录人信息
        JSONObject tokenJSONObject = new JSONObject(token);
        String json = tokenJSONObject.getString("token");
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(json);
        if (null == tokenEntity) {
            return Result.error(1,"token已过期，请重新登录！");
        }
        // 把课程id和用户id加入到数据库表中
        Map<String,Object> params = new HashMap<>();
        params.put("userId",tokenEntity.getUserId());
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        PageUtils page = xryCourseTeacherUserService.appPageListTeacherByUserId(params);
        return Result.ok().put("page", page);
    }

    /**
     * app端用户删除已经关注的讲师
     * @param ids
     * @return
     */
    @SysLog("app端用户删除已经关注的讲师")
    @PostMapping("/appDelTeacherById")
    public Result appDelTeacherById(@RequestParam String token, Long[] ids){
        // 从token中获取登录人信息
        JSONObject tokenJSONObject = new JSONObject(token);
        String json = tokenJSONObject.getString("token");
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(json);
        if (null == tokenEntity) {
            return Result.error(1,"token已过期，请重新登录！");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        xryCourseTeacherUserService.appDelTeacherById(params);
        return Result.ok();
    }
    
}
