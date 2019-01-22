package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
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
    public Result insertCommentByUserId(Long commentId, @RequestParam Integer type, @RequestParam  String objId, @RequestParam  Integer starLevel,
                                        @RequestParam String detail,HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                String userId = users.getId();
                Map<String, Object> params = new HashMap<>();
                params.put("objId", objId);
                params.put("userId", userId);
                params.put("starLevel", starLevel);
                params.put("detail", detail);
                // 判断是评论是修改还是添加
                Map<String, Object> comment = xryCommentService.selectCommentByUserIdAndObjId(params);
//                XryCommentEntity commentEntity = xryCommentService.selectById(commentId);
                if (null != comment) {
                    // 修改，只能修改一次
                    if (null != comment.get("update_time")) {
                        return Result.error(203, "评论只可以修改一次哦");
                    } else {
                        params.put("commentId", commentId);
                        xryCommentService.updateByCommentId(params);
                    }
                } else {
                    // 评论添加
                    params.put("type", type);
                    xryCommentService.insertCommentByUserId(params);
                }
            }
        }
        return Result.ok();
    }

    /**
     * app端进入评论详情
     * @param commentId
     * @return
     */
    @GetMapping("/comment/queryCommentById")
    @ApiOperation(value = "app端进入评论详情", notes = "app端进入评论详情")
    public Result queryCommentById(@RequestParam Long commentId) {
//        XryCommentEntity comment = xryCommentService.queryById(commentId);
        return Result.ok();
    }


}
