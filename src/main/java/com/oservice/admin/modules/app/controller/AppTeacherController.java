package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
    private static final Integer TEACHER_FOCUS_FLAG = 0;
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private XryUserAttentionService xryUserAttentionService;
    @Resource
    private ShiroService shiroService;


    /**
     * 保存讲师认证信息
     *
     * @param params
     * @return
     */
    @SysLog("保存讲师认证信息")
    @PostMapping("/appTeacherCertificate")
    @ApiOperation(value="用户申请为讲师、信息保存到数据库",notes="params：分类信息json对象，params包含讲师信息的参数，必填")
    public Result appTeacherCertificate(@RequestParam String[] params) {
        xryTeacherService.save(params);
        return Result.ok();
    }

    /**
     * app查询"我的关注"列表
     * @return
     */
    @SysLog("app查询'我的关注'列表")
    @PostMapping("/appPageListTeacherByUserId")
    @ApiOperation(value="查询用户已关注的讲师列表",notes="pageNo：页码，必填；pageSize：单个页面的列表数量，必填")
    public Result appPageListTeacherByUserId(@RequestParam Integer pageNo, @RequestParam Integer pageSize, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("userId", userId);
        List<Map<String, Object>> attentionTeacherList = xryTeacherService.appPageListTeacherByUserId(params);
        return Result.ok().put("attentionTeacherList", attentionTeacherList);
    }

    /**
     * app查询'明星讲师'列表:固定6个
     * @return
     */
    @SysLog("app查询'明星讲师'列表")
    @PostMapping("/appListStarTeacherByUserId")
    @ApiOperation(value="首页明星讲师列表",notes="不需要任何参数")
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
    @ApiOperation(value="用户关注讲师的操作接口",notes="teacherId：是被关注的讲师id，必填")
    public Result appTeacherAttentionByTeacherId(@RequestParam String teacherId, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        XryUserAttentionEntity isAttention = xryUserAttentionService.isAttentionByUserIdAndTeacherId(teacherId, userId);
        if (null != isAttention) {
            return Result.error(3, "该用户已经关注了讲师，不能再次关注");
        }
        params.put("userId", userId);
        params.put("objId", teacherId);
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
    @ApiOperation(value="用户取消已经关注的讲师的操作接口",notes="attentionId：是用户关注表中对应讲师的id，必填；teacherId：是被关注的讲师id，必填")
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
    @ApiOperation(value="进入讲师主页的接口",notes="teacherId：讲师的id，必填")
    public Result appQueryTeacherDetailByTeacherId(@RequestParam String teacherId, HttpServletRequest request){
        // 1、讲师详情
        Map<String, Object> teacherDetail = xryTeacherService.appQueryTeacherDetailByTeacherId(teacherId);
        // 2、判断该用户是否关注了该讲师
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            Integer isAttention = 0;
            // 根据用户id和讲师id查询该用户是否关注了该讲师
            XryUserAttentionEntity userAttention = xryUserAttentionService.isAttentionByUserIdAndTeacherId(teacherId, users.getId());
            if (null != userAttention) {
                teacherDetail.put("attentionId", userAttention.getId());
                isAttention = 1;
            }
            teacherDetail.put("isAttention", isAttention);
        }
        // 查询讲师的关注人数列表
        List<XryUserAttentionEntity> teacherAttentionList = xryUserAttentionService.countAttentionByTeacherId(teacherId);
        Integer teacherAttentionCount = teacherAttentionList.size();
        teacherDetail.put("teacherAttentionCount", teacherAttentionCount);
        teacherDetail.put("teacherId", teacherId);

        // 3、她/他主讲的课程列表
        List<Map<String, Object>> teacherRelatedList = xryTeacherService.listTeacherCourseByTeacherId(teacherId);
        Map<String, Object> params = new HashMap<>();
        params.put("teacherDetail", teacherDetail);
        params.put("teacherRelatedList", teacherRelatedList);
        return Result.ok().put("teacherMainPage", params);
    }


}
