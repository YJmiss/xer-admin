package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
    @Resource
    private XryRecordService xryRecordService;
    @Resource
    private XryVideoService xryVideoService;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private XryOrganizationService xryOrganizationService;

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
     * 审核系统->课程审核
     * @param record
     * @return
     */
    @SysLog("审核系统")
    @PostMapping("/examine")
    @RequiresPermissions("xry:record:examine")
    public Result examine(@RequestBody XryRecordEntity record) {
        Integer type = record.getType();
        if (1 == type) {
            // 修改课程在数据库的状态
            xryCourseService.recordExamineInfo(record);
        } else if (2 == type) {
            // 修改视频在数据库的状态
            xryVideoService.recordExamineInfo(record);
        } else if (3 == type) {
            // 修改讲师在数据库的状态
            xryTeacherService.recordExamineInfo(record);
        } else {
            // 修改机构在数据库的状态
            xryOrganizationService.recordExamineInfo(record);
        }
        // 第二步：记录视频审核信息到记录表
        Long userId = getUserId();
        xryRecordService.recordExamine(record,userId);
        return Result.ok("操作成功");
    }

    @SysLog("详情")
    @GetMapping("/detail")
    @RequiresPermissions("xry:record:detail")
    public Result delete(Long recordId) {
        String detailBatch = xryRecordService.detailBatch(recordId);
        return Result.ok().put("detailBatch", detailBatch);
    }

}
