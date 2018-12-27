package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCommentQuestionEntity;
import com.oservice.admin.modules.sys.service.XryCommentQuestionService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import io.swagger.annotations.Api;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 系统用户
 * 常见问题的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/question")
@Api(description = "常见问题的控制器")
public class XryCommentQuestionController extends AbstractController {
    @Resource
    private XryCommentQuestionService xryCommentQuestionService;


    /**
     * 查询常见问题列表
     * @param params
     * @return
     */
    @SysLog("查询常见问题列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:question:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCommentQuestionService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存问题
     * @param xryCommentQuestion
     * @return
     */
    @SysLog("保存问题")
    @PostMapping("/save")
    @RequiresPermissions("xry:question:save")
    public Result save(@RequestBody XryCommentQuestionEntity xryCommentQuestion) {
        ValidatorUtils.validateEntity(xryCommentQuestion, AddGroup.class);
        xryCommentQuestionService.save(xryCommentQuestion);
        return Result.ok();
    }
    /**
     * 问题信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:question:info")
    public Result info(@PathVariable("id") Long id){
        XryCommentQuestionEntity commentQuestion = xryCommentQuestionService.queryById(id);
        return Result.ok().put("commentQuestion", commentQuestion);
    }

    /**
     * 修改问题
     * @param commentQuestion
     * @return
     */
    @SysLog("修改问题")
    @PostMapping("/update")
    @RequiresPermissions("xry:question:update")
    public Result update(@RequestBody XryCommentQuestionEntity commentQuestion){
        ValidatorUtils.validateEntity(commentQuestion, UpdateGroup.class);
        xryCommentQuestionService.update(commentQuestion);
        return Result.ok();
    }

    /**
     * 删除问题
     * @param ids
     * @return
     */
    @SysLog("删除问题")
    @PostMapping("/delete")
    @RequiresPermissions("xry:question:delete")
    public Result delete(@RequestBody Long[] ids){
        xryCommentQuestionService.deleteBatch(ids);
        return Result.ok();
    }

}
