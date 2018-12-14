package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app用户表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/user")
public class XryUserController extends AbstractController {
    /** 讲师推荐 */
    final static Integer RECOMMEND_USER = 1;
    /**  取消推荐 */
    final static Integer CANCEL_RECOMMEND = 0;
    @Resource
    private XryUserService xryUserService;

    /**
     * 查询app用户列表
     * @param params
     * @return
     */
    @SysLog("查询app用户列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:user:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryUserService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 删除app用户
     * @param ids
     * @return
     */
    @SysLog("删除app用户")
    @PostMapping("/delete")
    @RequiresPermissions("xry:user:delete")
    public Result delete(@RequestBody String[] ids) {
        xryUserService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造用户树
     * @return
     */
    @SysLog("用户树")
    @GetMapping("/treeUser")
    @RequiresPermissions("xry:user:treeUser")
    public Result treeUser() {
        List<XryUserEntity> userList = xryUserService.treeUser();
        return Result.ok().put("userList", userList);
    }

    /**
     * 讲师推荐
     * @param ids
     * @return
     */
    @SysLog("讲师推荐")
    @PostMapping("/recommendUser")
    @RequiresPermissions("xry:user:recommendUser")
    public Result recommendUser(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("recommend",RECOMMEND_USER);
        xryUserService.updateUserRecommend(params);
        return Result.ok();
    }

    /**
     * 取消推荐
     * @param ids
     * @return
     */
    @SysLog("取消推荐")
    @PostMapping("/cancelRecommend")
    @RequiresPermissions("xry:user:cancelRecommend")
    public Result cancelRecommend(@RequestBody String[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("recommend",CANCEL_RECOMMEND);
        xryUserService.updateUserRecommend(params);
        return Result.ok();
    }
    
}
