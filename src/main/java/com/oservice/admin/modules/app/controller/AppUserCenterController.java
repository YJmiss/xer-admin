package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
