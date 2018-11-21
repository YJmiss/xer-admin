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
 * 审核记录表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/record")
public class XryRecordController extends AbstractController {
    @Resource
    private XryRecordService xryRecordService;

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

}
