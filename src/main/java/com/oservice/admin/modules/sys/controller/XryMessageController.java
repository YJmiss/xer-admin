package com.oservice.admin.modules.sys.controller;


import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.config.MessageWebSocket;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryCourseTeacherUserService;
import com.oservice.admin.modules.sys.service.XryMessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    /** 发布消息 */
    final static Integer PUBLISH_MESSAGE = 1;
    /** 取消发布消息 */
    final static Integer CANCEL_PUBLISH = 0;
    @Resource
    private XryMessageService xryMessageService;
    @Resource
    private XryCourseTeacherUserService xryCourseTeacherUserService;

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
     * @param messageEntity
     * @return
     */
    @SysLog("保存消息")
    @PostMapping("/save")
    @RequiresPermissions("xry:message:save")
    public Result save(@RequestBody XryMessageEntity messageEntity) throws Exception {
        ValidatorUtils.validateEntity(messageEntity, AddGroup.class);
        messageEntity.setCreated(new Date());
        xryMessageService.saveAndGetId(messageEntity);
        // 保存记录后返回自增的id
        Long messageId  = messageEntity.getId();
        // 查询出当前保存的记录并发消息到客户端  useGeneratedKeys="true"
        JSONObject message = new JSONObject(xryMessageService.seleMessageById(messageId));
        // 指定发送消息（课程消息、讲师关注）
        Integer msgType = message.getInt("msg_type");
        if (1 == msgType) {
            // 指定发送消息->课程消息
            // 查询出报名学习该课程的所有用户
            Long courseId = message.getLong("obj_id");
            List<String> userIds = xryCourseTeacherUserService.listUserIdByCourseId(courseId);
            new MessageWebSocket().sendToUser(String.valueOf(message),userIds);
        } else if (2 == msgType) {
            // 指定发送消息->讲师关注
            String teacherId = message.getString("user_id");
            List<String> userIds = xryCourseTeacherUserService.listUserIdByTeacherId(teacherId);
            new MessageWebSocket().sendToUser(String.valueOf(message),userIds);
        } else {
            //广播发送消息->平台消息
            new MessageWebSocket().sendMessage(String.valueOf(message));
        }
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
