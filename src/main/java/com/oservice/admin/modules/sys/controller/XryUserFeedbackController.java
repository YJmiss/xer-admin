package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * 用户反馈的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/feedback")
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
    @RequiresPermissions("xry:feedback:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryUserFeedbackService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 反馈信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:feedback:info")
    public Result info(@PathVariable("id") Long id){
        XryUserFeedbackEntity userFeedback = xryUserFeedbackService.queryById(id);
        return Result.ok().put("userFeedback", userFeedback);
    }

    /**
     * 删除反馈
     * @param ids
     * @return
     */
    @SysLog("删除反馈")
    @PostMapping("/delete")
    @RequiresPermissions("xry:feedback:delete")
    public Result delete(@RequestBody Long[] ids){
        xryUserFeedbackService.deleteBatch(ids);
        return Result.ok();
    }

}
