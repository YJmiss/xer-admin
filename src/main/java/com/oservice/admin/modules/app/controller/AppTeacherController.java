package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.service.*;
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
public class AppTeacherController extends AbstractController {
    /** 课程加入学习的标识符 */
    final static Integer COURSE_JOIN_STUDY = 1;
    /** 讲师关注的数据库标识符 */
    private static final Integer TEACHER_FOCUS_FLAG = 2;
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private XryUserAttentionService xryUserAttentionService;   // 关注表


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
     * app查询讲师列表
     * @return
     */
    @SysLog("app查询讲师列表")
    @GetMapping("/appPageListByUserId")
    public Result appPageListByUserId(@RequestParam Integer pageNo, Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("userId", getAppUserId());
        List<Map<String, Object>> orgList = xryTeacherService.appPageListByUserId(params);
        return Result.ok().put("orgList", orgList);
    }

    /**
     * app端讲师关注
     *
     * @param teacherId 讲师id
     * @param isSelect  是否关注
     * @return
     */
    @SysLog("app端讲师关注")
    @PostMapping("/appJoinCourseStudy")
    public Result appJoinCourseStudy(String teacherId, boolean isSelect) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getAppUserId());
        params.put("teacherId", teacherId);
        params.put("type", TEACHER_FOCUS_FLAG);
        Integer isSuccess = xryUserAttentionService.appSaveTeacher(params);
        // 给对应的讲师计数+1
        xryTeacherService.updateTeacherAttention(teacherId, 1);
        if (1 == isSuccess) {
            return Result.ok().put("1", "讲师关注成功");
        } else {
            return Result.error(2, "讲师关注失败");
        }
    }

    /**
     * app端根据用户查询用户关注的讲师列表
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @SysLog("app端根据用户查询用户关注的讲师列表")
    @GetMapping("/appPageListTeacherByUserId")
    public Result appPageListTeacherByUserId(Integer pageNo, Integer pageSize) {
        // 把课程id和用户id加入到数据库表中
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getAppUserId());
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        PageUtils page = xryUserAttentionService.appPageListTeacherByUserId(params);
        return Result.ok().put("page", page);
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


}
