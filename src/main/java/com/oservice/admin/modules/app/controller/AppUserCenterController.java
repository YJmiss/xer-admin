package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app端个人中心的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appUserCenter")
@Api(description = "app端个人中心的控制器")
public class AppUserCenterController extends AbstractController {
     @Resource
     private XryCourseService xryCourseService;
     @Resource
     private XryCourseCatService xryCourseCatService;
     @Resource
     private ShiroService shiroService;
     @Resource
     private XryTeacherService xryTeacherService;
     @Resource
     private XryUserService xryUserService;
     @Resource
     private XryUserApplicantService xryUserApplicantService;

    /**
     * 用户进入修改资料页面查询用户信息
     * @param request
     * @return
     */
    @GetMapping("/userCenter/queryUserInfoByUserId")
    @ApiOperation(value = "用户进入修改资料页面查询用户信息（昵称、性别、邮箱、头像）", notes = "需要在请求头里加token参数")
    public Result queryUserInfoByUserId(HttpServletRequest request) {
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
        XryUserEntity userInfo = xryUserService.selectById(userId);
        return Result.ok().put("userInfo", userInfo);
    }

    /**
     * 根据userId查询用户已经选择喜好的课程类目
     * 不需要根据类目分类显示
     * @return
     */
    @GetMapping("/userCenter/listCourseCatByUserId")
    @ApiOperation(value = "查询用户已经选择喜好的课程类目，即“我的兴趣”", notes = "需要在请求头里加token参数")
    public Result listCourseCatByUserId(HttpServletRequest request) {
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
        params.put("userId",userId);
        XryRecommendEntity recommend = xryCourseService.listRecommendCourseCatByUserId(params);
        String[] courseCatIds = recommend.getCatId().split(",");
        List<XryCourseCatEntity> courseCatList = new ArrayList<>();
        if (courseCatIds.length > 0) {
            for (String courseCatId : courseCatIds) {
                XryCourseCatEntity courseCat = xryCourseCatService.selectById(courseCatId);
                courseCatList.add(courseCat);
            }
        }
        return Result.ok().put("courseCatList",courseCatList);
    }

    /**
     * 查询用户已经关注的讲师数
     * @param request
     * @return
     */
    @GetMapping("/userCenter/countUserApplicantByUserId")
    @ApiOperation(value = "查询用户已经关注的讲师数", notes = "需要在请求头里加token参数")
    public Result countUserApplicantByUserId(HttpServletRequest request) {
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
        Integer userApplicantCount = xryTeacherService.countUserApplicantByUserId(userId);

        return Result.ok().put("userApplicantCount", userApplicantCount);
    }

    /**
     * 个人中心修改个人资料接口
     * @param params
     * @param request
     * @return
     */
    @GetMapping("/userCenter/updateUserInfoByUserId")
    @ApiOperation(value = "个人中心修改个人资料接口", notes = "params：携带昵称、性别、邮箱信息的参数；需要在请求头里加token参数")
    public Result updateUserInfoByUserId(@RequestParam String params, HttpServletRequest request) {
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
        xryUserService.updateUserInfoByUserId(params, userId);
        return Result.ok();
    }

    /**
     * 个人中心头像上传
     * @param newHeadImg
     * @param request
     * @return
     */
    @GetMapping("/userCenter/updateUserHeadImgByUserId")
    @ApiOperation(value = "个人中心头像上传接口", notes = "newHeadImg：是头像的URL；需要在请求头里加token参数")
    public Result updateUserHeadImgByUserId(@RequestParam String newHeadImg, HttpServletRequest request) {
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
        xryUserService.updateUserHeadImgByUserId(newHeadImg, userId);
        return Result.ok();
    }

    /**
     * 清除最近学习课程
     * 不删除数据库数据
     * @param 
     * @param request
     * @return
     */
    @GetMapping("/userCenter/removeUserCourseByUserId")
    @ApiOperation(value = "清除最近学习课程，不删除数据库数据", notes = "需要在请求头里加token参数")
    public Result removeUserCourseByUserId(HttpServletRequest request) {
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
        xryUserApplicantService.removeUserCourseByUserId(userId);
        return Result.ok();
    }

    /**
     * 查询最近浏览课程列表
     * @param ids
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @GetMapping("/userCenter/listCourseByUserIdAndCourseId")
    @ApiOperation(value = "查询最近浏览课程列表", notes = "ids：储存在localStorage里的课程id，数组；需要在请求头里加token参数")
    public Result listCourseByUserIdAndCourseId(@RequestParam Long[] ids, @RequestParam Integer pageNo, @RequestParam Integer pageSize, HttpServletRequest request) {
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
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put(userId, userId);
        params.put("pageSize", pageSize);
        params.put("pageNo", (pageNo - 1) * pageSize);
        List<Map<String, Object>> courseList = xryCourseService.listCourseByUserIdAndCourseId(params);
        return Result.ok().put("courseList", courseList);
    }

}
