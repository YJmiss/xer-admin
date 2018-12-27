package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryUserCollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app端用户收藏的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appUserCollect")
@Api(description = "app端用户收藏的控制器")
public class AppUserCollectController extends AbstractController {
    @Resource
    private ShiroService shiroService;
    @Resource
    private XryUserCollectService xryUserCollectService;

    /**
     * app端用户收藏
     * @param objId
     * @return
     */
    @PostMapping("/appUserCollectByUserId")
    @ApiOperation(value="app端用户收藏接口",notes="objId：是被收藏对象的id，必填")
    public Result appUserCollectByUserId(@RequestParam String objId, HttpServletRequest request) {
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
        xryUserCollectService.appUserCollectByUserId(userId,objId);
        return Result.ok();
    }

    /**
     * app端删除收藏列表的一个收藏
     * @param collectId
     * @param request
     * @return
     */
    @PostMapping("/appDelUserCollectByCollectId")
    @ApiOperation(value = "app端删除收藏列表的一个收藏", notes = "collectId：收藏id，必填；在请求头里加上token")
    public Result appDelUserCollectByCollectId(@RequestParam Long collectId, HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
        }
        xryUserCollectService.appDelUserCollectByCollectId(collectId);
        return Result.ok();
    }

    /**
     * app端查询用户收藏列表
     * @param request
     * @return
     */
    @PostMapping("/appListUserCollectByUserId")
    @ApiOperation(value = "app端查询用户收藏列表", notes = "在请求头里加上token，不需要传参数")
    public Result appListUserCollectByUserId(HttpServletRequest request) {
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
        List<Map<String, Object>> collectCourseList = xryUserCollectService.appListUserCollectByUserId(userId);
        if (collectCourseList.size() > 0) {
            for (Map<String, Object> map : collectCourseList) {
                // 根据课程id查询课程报名人数
                Long courseId = Long.valueOf(String.valueOf(map.get("obj_id")));
                Integer courseApplicantCount = xryUserCollectService.countCourseApplicantByCourseId(courseId);
                map.put("courseApplicantCount", courseApplicantCount);
            }
        }
        return Result.ok().put("collectCourseList", collectCourseList);
    }

}
