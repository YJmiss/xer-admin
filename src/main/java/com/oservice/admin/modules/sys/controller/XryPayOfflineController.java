package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 系统用户
 * 线下支付表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/payOffline")
public class XryPayOfflineController extends AbstractController {
    /** 课程加入学习的标识符 */
    final static Integer COURSE_JOIN_STUDY = 0;
    @Resource
    private XryPayOfflineService xryPayOfflineService;
    @Resource
    private XryUserApplicantService xryUserApplicantService;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private SolrJDao solrJDao;

    /**
     * 查询线下支付列表
     * @param params
     * @return
     */
    @SysLog("查询线下支付列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:payOffline:list")
    public Result list(@RequestParam Map<String, Object> params) {
        PageUtils page = xryPayOfflineService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 删除线下支付
     * @param ids
     * @return
     */
    @SysLog("删除线下支付")
    @PostMapping("/delete")
    @RequiresPermissions("xry:payOffline:delete")
    public Result delete(@RequestBody Long[] ids) {
//        xryPayOfflineService.deleteBatch(ids);
        Map<String, Object> params = new HashMap<>();
        for (Long id : ids) {
            // 第一步：从课程报名表中删除对应信息
            XryPayOfflineEntity xryPayOffline = xryPayOfflineService.selectById(id);
            Long courseId = xryPayOffline.getXcId();
            params.put("userId", xryPayOffline.getXuId());
            params.put("objId", courseId);
            xryUserApplicantService.delCourseByUserIdAndCourseId(params);
            // 第二步：从线下支付表中删除对应信息
            xryPayOfflineService.deleteById(id);
            // 第三步：给课程表中的计数-1
            xryCourseService.updateCourseApplicationCount(courseId, 2);
        }
        return Result.ok();
    }

    /**
     * 保存线下支付
     * @param xryPayOffline
     * @return
     */
    @SysLog("保存线下支付")
    @PostMapping("/save")
    @RequiresPermissions("xry:payOffline:save")
    public Result save(@RequestBody XryPayOfflineEntity xryPayOffline) {
        ValidatorUtils.validateEntity(xryPayOffline, AddGroup.class);
        xryPayOffline.setSuId(String.valueOf(getUser().getUserId()));
        xryPayOffline.setCreateTime(new Date());
        xryPayOfflineService.save(xryPayOffline);
        // 保存成功后，把课程id和用户id添加到报名表中
        Map<String, Object> params = new HashMap<>();
        Long courseId = xryPayOffline.getXcId();
        params.put("userId", xryPayOffline.getXuId());
        params.put("objId", courseId);
        params.put("objType", COURSE_JOIN_STUDY);
        Integer isSuccess = xryUserApplicantService.appSaveCourse(params);
        if (1 == isSuccess) {
            // 给课程计数+1
            xryCourseService.updateCourseApplicationCount(courseId, 1);
            XryCourseEntity course = xryCourseService.queryById(courseId);
            //得到课程的学习数
            try {
                solrJDao.update(courseId, course.getApplicantCount(), 1);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            return Result.ok().put("1", "课程加入学习成功");
        } else {
            return Result.error(2, "课程加入学习失败");
        }
    }

    /**
     * 线下支付信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:payOffline:info")
    public Result info(@PathVariable("id") Long id){
        XryPayOfflineEntity xryPayOffline = xryPayOfflineService.queryById(id);
        return Result.ok().put("xryPayOffline", xryPayOffline);
    }

    /**
     * 修改线下支付
     * @param xryPayOffline
     * @return
     *
     */
    @SysLog("修改线下支付")
    @PostMapping("/update")
    @RequiresPermissions("xry:payOffline:update")
    public Result update(@RequestBody XryPayOfflineEntity xryPayOffline){
        ValidatorUtils.validateEntity(xryPayOffline, UpdateGroup.class);
        xryPayOffline.setSuId(String.valueOf(getUser().getUserId()));
        xryPayOfflineService.update(xryPayOffline);
        return Result.ok();
    }

}
