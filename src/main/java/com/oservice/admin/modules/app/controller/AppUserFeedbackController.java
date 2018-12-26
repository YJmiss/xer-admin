package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryUserCollectService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app端用户反馈问题的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appUserFeedback")
@Api(description = "app端用户反馈问题的控制器")
public class AppUserFeedbackController extends AbstractController {
    @Resource
    private ShiroService shiroService;
    @Resource
    private XryUserFeedbackService xryUserFeedbackService;

    /**
     * app端用户反馈问题
     * @param params
     * @return
     */
    @GetMapping("/appUserFeedbackByUserId")
    @ApiOperation(value="app端用户反馈问题接口",notes="params：携带用户反馈图片（可空）、反馈具体信息，必填")
    public Result appUserFeedbackByUserId(@RequestParam String params, HttpServletRequest request) {
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
        xryUserFeedbackService.appUserFeedbackByUserId(userId, params);
        return Result.ok();
    }

}
