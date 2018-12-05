package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.service.XryCommentService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryRecordService;
import com.oservice.admin.modules.sys.service.XryVideoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * 用户评论表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/comment")
public class XryCommentController extends AbstractController {
    /** 不显示评论 */
    final static Integer HIDE_COMMENT = 0;
    /** 恢复评论显示 */
    final static Integer RECOVER_COMMENT = 1;
    /** 成功 */
    final static Integer SUCCESS = 1;
    @Resource
    private XryCommentService xryCommentService;

    /**
     * 查询评论列表
     * @param params
     * @return
     */
    @SysLog("查询评论列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:comment:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryCommentService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 删除评论
     * @param ids
     * @return
     */
    @SysLog("删除评论")
    @PostMapping("/delete")
    @RequiresPermissions("xry:comment:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryCommentService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 不显示评论
     * @param ids
     * @return
     */
    @SysLog("不显示评论")
    @PostMapping("/hideComment")
    @RequiresPermissions("xry:comment:hideComment")
    public Result hideComment(@RequestBody Long[] ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        params.put("flag", HIDE_COMMENT);
        params.put("publishDate",new Date());
        xryCommentService.updateCommentStatus(params);
        return Result.ok("操作成功");
    }

    /**
     * 恢复评论显示
     * @param ids
     * @return
     */
    @SysLog("恢复评论显示")
    @PostMapping("/recoverComment")
    @RequiresPermissions("xry:comment:recoverComment")
    public Result recoverComment(@RequestBody Long[] ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        params.put("flag", RECOVER_COMMENT);
        params.put("publishDate",new Date());
        xryCommentService.updateCommentStatus(params);
        return Result.ok("操作成功");
    }

    /**
     * app端用户评论保存
     * @param comment
     * @return
     */
    @SysLog("app端用户评论保存")
    @PostMapping("/saveComment")
    @RequiresPermissions("xry:comment:saveComment")
    public Result saveComment(@RequestBody XryCommentEntity comment) {
        ValidatorUtils.validateEntity(comment, AddGroup.class);
        if (null != comment.getId()) {
            // 修改保存
            xryCommentService.update(comment);
            return Result.ok().put("success", SUCCESS);
        } else {
            // 添加保存
            comment.setUserId(getAppUserId());
            xryCommentService.save(comment);
            return Result.ok().put("success", SUCCESS);
        }
    }

    /**
     * app端查询一条评论
     * @param id
     * @return
     */
    @SysLog("app端查询一条评论")
    @GetMapping("/queryCommentById/{id}")
    @RequiresPermissions("xry:comment:queryCommentById")
    public Result queryCommentById(@PathVariable("id") Long id){
        XryCommentEntity comment = xryCommentService.queryById(id);
        return Result.ok().put("comment", comment);
    }

}
