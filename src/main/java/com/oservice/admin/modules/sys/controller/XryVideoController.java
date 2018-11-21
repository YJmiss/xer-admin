package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
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
 * 视频表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/video")
public class XryVideoController extends AbstractController {
    /** 课程审核通过常量 */
    final static  Integer VIDEO_EXAMINE_PASS = 3;
    /** 课程审核驳回常量 */
    final static  Integer VIDEO_EXAMINE_REJECT = 4;
    /** 视频审核的标识符 */
    final static  Integer VIDEO_EXAMINE_FLAG = 2;
    @Resource
    private XryVideoService xryVideoService;
    @Resource
    private XryRecordService xryRecordService;

    /**
     * 查询视频列表
     * @param params
     * @return
     */
    @SysLog("查询视频列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:video:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryVideoService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存视频
     * @param video
     * @return
     */
    @SysLog("保存视频")
    @PostMapping("/save")
    @RequiresPermissions("xry:video:save")
    public Result save(@RequestBody XryVideoEntity video) {
        ValidatorUtils.validateEntity(video, AddGroup.class);
        video.setCreated(new Date());
        video.setUpdated(new Date());
        xryVideoService.save(video);
        return Result.ok();
    }

    /**
     * 视频信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:video:info")
    public Result info(@PathVariable("id") Long id) {
        XryVideoEntity video = xryVideoService.queryById(id);
        return Result.ok().put("video", video);
    }

    /**
     * 修改视频
     * @param video
     * @return
     */
    @SysLog("修改视频")
    @PostMapping("/update")
    @RequiresPermissions("xry:video:update")
    public Result update(@RequestBody XryVideoEntity video) {
        ValidatorUtils.validateEntity(video, UpdateGroup.class);
        xryVideoService.update(video);
        return Result.ok();
    }

    /**
     * 删除视频
     * @param ids
     * @return
     */
    @SysLog("删除视频")
    @PostMapping("/delete")
    @RequiresPermissions("xry:video:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryVideoService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 审核系统->视频审核：3
     * @param ids
     * @return
     */
    @SysLog("审核系统->视频审核")
    @PostMapping("/examinePass")
    @RequiresPermissions("xry:video:examine:pass")
    public Result examinePass(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",VIDEO_EXAMINE_PASS);
        xryVideoService.updateVideoStatus(params);
        // 记录课程审核事件
        params.put("userId",getUserId());
        params.put("type",VIDEO_EXAMINE_FLAG);
        xryRecordService.recordCourseExamine(params);
        return Result.ok("审核通过");
    }

    @SysLog("审核系统->审核驳回")
    @PostMapping("/examineReject")
    @RequiresPermissions("xry:video:examine:reject")
    public Result examineReject(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",VIDEO_EXAMINE_REJECT);
        xryVideoService.updateVideoStatus(params);
        // 记录课程审核事件
        params.put("userId",getUserId());
        params.put("type",VIDEO_EXAMINE_FLAG);
        xryRecordService.recordCourseExamine(params);
        return Result.ok("审核驳回");
    }
}
