package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * app评论的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appComment")
@Api(description = "评论的控制器")
public class AppCommentController extends AbstractController {
    @Resource
    private XryCommentService xryCommentService;
    @Resource
    private ShiroService shiroService;

    /**
     * app端用户评论保存
     * @param type
     * @param objId
     * @param starLevel
     * @param detail
     * @param request
     * @return
     */
    @PostMapping("/comment/insertCommentByUserId")
    @ApiOperation(value = "app端用户评论保存方法", notes = "params：携带评论参数（type、objId、starLevel、detail），必填")
    public Result insertCommentByUserId(@RequestParam Integer type, @RequestParam  String objId, @RequestParam  Integer starLevel,
            @RequestParam String detail,HttpServletRequest request) {
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            //token失效
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        Map<String, Object> params = new HashMap<>();
        params.put("type", type);
        params.put("objId", objId);
        params.put("starLevel", starLevel);
        params.put("detail", detail);
        xryCommentService.insertCommentByUserId(params, userId);
        return Result.ok();
    }

    /**
     * app端进入评论详情
     * @param commentId
     * @return
     */
    @GetMapping("/comment/queryCommentById")
    @ApiOperation(value = "", notes = "")
    public Result queryCommentById(@RequestParam Long commentId) {
//        XryCommentEntity comment = xryCommentService.queryById(commentId);
        return Result.ok();
    }


}
