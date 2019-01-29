package com.oservice.admin.modules.app.controller;

import com.google.gson.Gson;
import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.DistributionGroup;
import com.oservice.admin.modules.app.information.DistributionConfig;
import com.oservice.admin.modules.app.service.DistributionService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 分销控制器
 * @author: YJmiss
 * @create: 2019-01-10 10:28
 **/
@Api(description = "分销控制器")
@RestController
@RequestMapping("/sys/distribution")
public class DistributionController extends AbstractController {
    private final static String KEY = ConfigConstant.DISTRIBUTIONCONFIG_CONFIG_KEY;
    @Resource
    private SysConfigService sysConfigService;
    @Resource
    private DistributionService distributionService;

    /**
     * 分销配置信息
     */
    @GetMapping("/config")
    @RequiresPermissions("sys:distribution:all")
    public Result config() {
        DistributionConfig config = sysConfigService.getConfigObject(KEY, DistributionConfig.class);
        return Result.ok().put("config", config);
    }

    /**
     * 保存分销配置信息
     */
    @PostMapping("/saveConfig")
    @RequiresPermissions("sys:distribution:all")
    public Result saveConfig(@RequestBody DistributionConfig config) {
        //校验类型
        ValidatorUtils.validateEntity(config);
        //校验分销配置数据
        ValidatorUtils.validateEntity(config, DistributionGroup.class);
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
        return Result.ok();
    }

    /**
     * 后台列表所有订单
     */
    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("sys:distribution:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = distributionService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 推广信息
     */
    @GetMapping("/courseList")
    @ApiOperation(value = "推广中心数据接口")
    public Result courseList() {
        List<Map<String, Object>> list = distributionService.courseList();
        if (list == null) {
            return Result.error(203, "搜索服务异常，联系管理员");
        }
        return Result.ok().put("courseList", list);
    }

    /**
     * 我的收益页面数据接口
     */
    @GetMapping("/myEarnings")
    @ApiOperation(value = "我的收益页面数据接口")
    public Result myEarnings() {
        Map<String, Object> map = distributionService.myEarnings(getAppUserId());
        return Result.ok(map);
    }

    /**
     * 佣金收入明细接口
     */
    @GetMapping("/accountBalance")
    @ApiOperation(value = "佣金收入明细接口")
    public Result accountBalance() {
        List<Map<String, Object>> list = distributionService.accountBalance(getAppUserId());
        if (list == null || list.size() < 1) {
            return Result.ok();
        }
        return Result.ok().put("list", list);
    }

    /**
     * 用户提现and结算信息保存
     */
    @GetMapping("/informationSave")
    @ApiOperation(value = "用户结算信息保存")
    public Result informationSave(@RequestParam int type, String userName, String cardNumber, double amount) {

        return Result.ok();
    }

    /**
     * APP提现记录数据接口
     */
    @GetMapping("/getWithdrawalRecord")
    @ApiOperation(value = "APP提现记录数据接口")
    public Result getWithdrawalRecord() {

        return Result.ok();
    }
}
