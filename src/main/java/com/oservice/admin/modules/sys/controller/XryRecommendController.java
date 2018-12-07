package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统用户
 * 推荐表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/recommend")
public class XryRecommendController extends AbstractController {
    @Resource
    private XryCourseCatService xryCourseCatService;
    @Resource
    private XryCourseService xryCourseService;

    /**
     * 类目查询
     * @param params
     * @return
     */
    @SysLog("类目查询")
    @GetMapping("/appListCourseCat")
    public Result appListCourseCat(@RequestParam Map<String, Object> params){
        // 第一步：查询所有的课程类目
        PageUtils courseCatPage = xryCourseCatService.queryPage(new HashMap<>());
        List<XryCourseCatEntity> courseCatList = (List<XryCourseCatEntity>) courseCatPage.getList();
        // 循环取出子类目
        if (courseCatList.size() > 0) {
            for (XryCourseCatEntity catEntity : courseCatList) {
                catEntity.getParentId();
            }
        }
        
        return Result.ok().put("appCourseCatList", "");
    }

    /**
     * 根据userId查询用户已经选择喜好的课程类目
     * 不需要根据类目分类显示
     * @param userId
     * @return
     */
    @SysLog("查询用户已经选择喜好的课程类目")
    @GetMapping("/appListCourseCatByUserId/{userId}")
    public Result appListCourseCatByUserId(@PathVariable("userId") String userId){
        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        XryRecommendEntity recommend = xryCourseService.listRecommendCourseCatByUserId(params);
        String[] courseCatIds = recommend.getCatId().split("~");
        List<XryCourseCatEntity> courseCatList = new ArrayList<>();
        if (courseCatIds.length > 0) {
            for (String courseCatId : courseCatIds) {
                XryCourseCatEntity courseCat = xryCourseCatService.selectById(courseCatId);
                courseCatList.add(courseCat);
            }    
        }
        return Result.ok().put("courseCatList",courseCatList);
    }

    /**
     * 用户设置喜好课程，添加到数据库表中
     * @param params
     * @return
     */
    @SysLog("用户设置喜好课程类目保存")
    @PostMapping("/appInsertRecommendCourseCat")
    public Result appInsertRecommendCourseCat(@RequestParam Map<String, Object> params){
        // 判断用户是添加还是修改
        XryRecommendEntity recommend = xryCourseService.listRecommendCourseCatByUserId(params);
        if (null != recommend) {
            // 用户喜好修改
            xryCourseService.appInsertRecommend(params);
        } else {
            // 用户喜好添加（第一次设置）
            params.put("created",new Date());
            xryCourseService.appUpdateRecommend(params);
        }
        return Result.ok();
    }
    
}
