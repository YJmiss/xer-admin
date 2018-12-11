package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import com.oservice.admin.modules.sys.service.XryMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * 消息表的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/message")
public class XryMessageController extends AbstractController {
    /**
     * 发布消息
     */
    final static Integer PUBLISH_MESSAGE = 1;
    /**
     * 取消发布消息
     */
    final static Integer CANCEL_PUBLISH = 0;
    @Resource
    private XryMessageService xryMessageService;

    /**
     * 查询消息列表
     *
     * @param params
     * @return
     */
    @SysLog("查询消息列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:message:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryMessageService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存消息
     *
     * @param message
     * @return
     */
    @SysLog("保存消息")
    @PostMapping("/save")
    @RequiresPermissions("xry:message:save")
    public Result save(@RequestBody XryMessageEntity message) {
        ValidatorUtils.validateEntity(message, AddGroup.class);
        message.setCreated(new Date());
        xryMessageService.save(message);

        // 调用发送消息的类
//        new WebSocketComponent().sendMessage(message);
        return Result.ok();
    }

    /**
     * 消息信息
     *
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:message:info")
    public Result info(@PathVariable("id") Long id) {
        XryMessageEntity message = xryMessageService.queryById(id);
        return Result.ok().put("message", message);
    }

    /**
     * 修改消息
     *
     * @param message
     * @return
     */
    @SysLog("修改消息")
    @PostMapping("/update")
    @RequiresPermissions("xry:message:update")
    public Result update(@RequestBody XryMessageEntity message) {
        ValidatorUtils.validateEntity(message, UpdateGroup.class);
        xryMessageService.update(message);
        return Result.ok();
    }

    /**
     * 删除消息
     *
     * @param ids
     * @return
     */
    @SysLog("删除消息")
    @PostMapping("/delete")
    @RequiresPermissions("xry:message:delete")
    public Result delete(@RequestBody Long[] ids) {
        xryMessageService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 发布消息：1
     *
     * @param ids
     * @return
     */
    @SysLog("发布消息")
    @PostMapping("/publishMessage")
    @RequiresPermissions("xry:message:publishMessage")
    public Result publishMessage(@RequestBody Long[] ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        params.put("flag", PUBLISH_MESSAGE);
        params.put("publishDate", new Date());
        xryMessageService.updateMessageStatus(params);
        return Result.ok();
    }

    /**
     * 取消发布消息：1
     *
     * @param ids
     * @return
     */
    @SysLog("取消发布消息")
    @PostMapping("/cancelPublish")
    @RequiresPermissions("xry:message:cancelPublish")
    public Result cancelPublish(@RequestBody Long[] ids) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        params.put("flag", CANCEL_PUBLISH);
        params.put("publishDate", new Date());
        xryMessageService.updateMessageStatus(params);
        return Result.ok();
    }

}
