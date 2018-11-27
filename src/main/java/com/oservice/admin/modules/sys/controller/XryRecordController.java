package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
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
 * 审核记录表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/record")
public class XryRecordController extends AbstractController {
    /** 课程审核通过常量 */
    final static  Integer VIDEO_EXAMINE_PASS = 3;
    /** 课程审核驳回常量 */
    final static  Integer VIDEO_EXAMINE_REJECT = 4;
    /** 视频审核的标识符 */
    final static  Integer VIDEO_EXAMINE_FLAG = 2;
    @Resource
    private XryRecordService xryRecordService;
    @Resource
    private XryVideoService xryVideoService;

    /**
     * 查询记录列表
     * @param params
     * @return
     */
    @SysLog("查询记录列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:record:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryRecordService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 删除记录
     * @param ids
     * @return
     */
    @SysLog("删除记录")
    @PostMapping("/delete")
    @RequiresPermissions("xry:record:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryRecordService.deleteBatch(ids);
        return Result.ok();
    }


    /**
     * 审核系统->视频审核
     * @param record
     * @return
     */
    @SysLog("审核系统->视频审核")
    @PostMapping("/videoExamine")
    @RequiresPermissions("xry:record:videoExamine")
    public Result videoExamine(@RequestBody XryRecordEntity record) {
        // 先修改视频对象的状态
        xryVideoService.updateVideoStatus(record);
        // 记录课程审核信息
        Long userId = getUserId();
        xryRecordService.recordCourseExamine(record,userId);
        return Result.ok("操作成功");
    }

}
