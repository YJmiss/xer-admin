package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统用户
 * 用户反馈的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/userFeedback")
@Api(description = "用户反馈的控制器")
public class XryUserFeedbackController extends AbstractController {
    @Resource
    private XryUserFeedbackService xryUserFeedbackService;

    /**
     * 查询用户反馈列表
     * @param params
     * @return
     */
    @SysLog("查询用户反馈列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:userFeedback:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryUserFeedbackService.queryPage(params);
        return Result.ok().put("page", page);
    }

}
