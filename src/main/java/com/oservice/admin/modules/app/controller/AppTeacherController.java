package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app端讲师的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appTeacher")
@Api(description = "APP讲师控制器")
public class AppTeacherController extends AbstractController {
    /** 讲师关注的数据库标识符 */
    private static final Integer TEACHER_FOCUS_FLAG = 2;
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private XryUserAttentionService xryUserAttentionService;  


    /**
     * 保存讲师认证信息
     *
     * @param params
     * @return
     */
    @SysLog("保存讲师认证信息")
    @PostMapping("/appSave")
    public Result appSave(@RequestParam String[] params) {
        xryTeacherService.save(params);
        return Result.ok();
    }

    /**
     * app查询"我的关注"列表
     * @return
     */
    @SysLog("app查询'我的关注'列表")
    @PostMapping("/appPageListTeacherByUserId")
    public Result appPageListTeacherByUserId(@RequestParam Integer pageNo, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("userId", getAppUserId());
        List<Map<String, Object>> attentionTeacherList = xryTeacherService.appPageListTeacherByUserId(params);
        return Result.ok().put("attentionTeacherList", attentionTeacherList);
    }

    /**
     * app查询'明星讲师'列表:固定6个
     * @return
     */
    @SysLog("app查询'明星讲师'列表")
    @PostMapping("/appListStarTeacherByUserId")
    public Result appListStarTeacherByUserId() {
        Map<String, Object> params = new HashMap<>();
        params.put("pageSize",6);
        List<Map<String, Object>> starTeacherList = xryTeacherService.appListStarTeacherByUserId(params);
        return Result.ok().put("starTeacherList", starTeacherList);
    }

    /**
     * app端讲师关注
     *
     * @param teacherId 讲师id
     * @return
     */
    @SysLog("app端讲师关注")
    @PostMapping("/appTeacherAttentionByTeacherId")
    public Result appTeacherAttentionByTeacherId(String teacherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getAppUserId());
        params.put("teacherId", teacherId);
        params.put("type", TEACHER_FOCUS_FLAG);
        xryUserAttentionService.appSaveTeacher(params);
        // 给对应的讲师计数+1
        xryTeacherService.updateTeacherAttention(teacherId, 1);
        return Result.ok();
    }

    /**
     * app端用户取消已经关注的讲师
     *
     * @param attentionId
     * @return
     */
    @SysLog("app端用户取消已经关注的讲师")
    @PostMapping("/appDelTeacherById")
    public Result appDelTeacherById(@RequestParam Long attentionId, String teacherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("attentionId", attentionId);
        xryUserAttentionService.appDelTeacherById(params);
        // 给对应的讲师计数-1
        xryTeacherService.updateTeacherAttention(teacherId, 2);
        return Result.ok();
    }

    /**
     * app端讲师主页
     * @param teacherId
     * @return
     */
    @SysLog("app端讲师主页")
    @PostMapping("/appQueryTeacherDetailByTeacherId")
    public Result appQueryTeacherDetailByTeacherId(@RequestParam String teacherId){
        // 1、讲师详情
        Map<String, Object> teacherDetail = xryTeacherService.appQueryTeacherDetailByTeacherId(teacherId);
        // 她/他主讲的课程
        List<Map<String, Object>> teacherRelatedList = xryTeacherService.listTeacherCourseByTeacherId(teacherId);
        Map<String, Object> params = new HashMap<>();
        params.put("teacherDetail", teacherDetail);
        params.put("teacherRelatedList", teacherRelatedList);
        return Result.ok().put("teacherMainPage", params);
    }


}
