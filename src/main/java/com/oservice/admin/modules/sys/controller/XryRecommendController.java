package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.ListUtil;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
import com.oservice.admin.modules.sys.service.SysUserTokenService;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.json.JSONArray;
import org.json.JSONObject;
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
    @Resource
    private SysUserTokenService sysUserTokenService;

    /**
     * 所有类目查询
     * @return
     */
    @GetMapping("/appListCourseCat")
    public Result appListCourseCat(){
        // 第一步：查询所有的课程类目
        List<Map<String, Object>> catList = xryCourseCatService.listCourseCat();
        // 构造一个存放父类目的容器
        List<Map<String, Object>> courseCatList = ListUtil.listToTreeList(catList,"id","parent_id","catList");
        
        return Result.ok().put("courseCatList", courseCatList);
    }

    /**
     * 用户设置喜好课程，添加到数据库表中
     * @param tokenJSON
     * @return
     */
    @SysLog("用户设置喜好课程类目保存")
    @PostMapping("/appInsertRecommendCourseCat")
    public Result appInsertRecommendCourseCat(@RequestBody String tokenJSON){
        JSONObject tokenJSONObject = new JSONObject(tokenJSON);
        JSONArray catList = (JSONArray) tokenJSONObject.get("catGroup");
        // 判断用户是添加还是修改
        Map<String,Object> params = new HashMap<>();
        params.put("userId",getAppUserId());
        params.put("catArr",catList.toString());
        XryRecommendEntity recommend = xryCourseService.listRecommendCourseCatByUserId(params);
        if (null != recommend) {
            // 用户喜好修改
            xryCourseService.appUpdateRecommend(params);
            System.out.println("---------------用户喜好修改成功----------------");
        } else {
            // 用户喜好添加（第一次设置）
            params.put("created",new Date());
            xryCourseService.appInsertRecommend(params);
            System.out.println("---------------用户喜好设置成功----------------");
        }
        return Result.ok().put("2","操作成功");
    }
    
}
