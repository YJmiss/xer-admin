package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
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
    @GetMapping("/detail")
    @RequiresPermissions("xry:feedback:detail")
    public Result detail(@RequestParam Long id){
        Map<String,Object> userFeedback = xryUserFeedbackService.queryByIdAndUserId(id);
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
    /**
     * 用户反馈回复
     * @param userFeedbackEntity
     * @return
     */
    @SysLog("用户反馈回复")
    @PostMapping("/replySave")
    @RequiresPermissions("xry:feedback:replySave")
    public Result update(@RequestBody XryUserFeedbackEntity userFeedbackEntity){
        ValidatorUtils.validateEntity(userFeedbackEntity, UpdateGroup.class);
        userFeedbackEntity.setCheckStatus(1);
        userFeedbackEntity.setReplyTime(new Date());
        xryUserFeedbackService.updateById(userFeedbackEntity);
        return Result.ok();
    }


}
