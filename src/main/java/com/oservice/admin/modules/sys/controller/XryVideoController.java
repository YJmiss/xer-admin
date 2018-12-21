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
    @Resource
    private XryVideoService xryVideoService;

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
     * @Description: 上传视频
     * @Param: [multipartFile, request]
     * @return: com.oservice.admin.common.utils.Result
     * @Author: YJmiss
     * @Date: 2018/11/23
     */
   /* @RequestMapping(value = "/upload", method = {RequestMethod.POST, RequestMethod.GET})
    public Result upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
        Map map = new HashMap<>();
        FileEntity entity;
        FileUploadTool fileUploadTool = new FileUploadTool();
        try {
            entity = fileUploadTool.createFile(multipartFile, request);
            return Result.ok().put("url", entity.getSize());
        }catch (Exception e){
            e.printStackTrace();
            //上传失败的map
            map.put("error", 1);
            map.put("message", "视频上传失败！");
        }
        return Result.ok(map);
    }*/
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
        // 视频修改后重置为未审核
        video.setStatus(1);
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

}
