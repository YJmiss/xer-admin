package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.ListUtil;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 系统用户
 * 推荐表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/recommend")
@Api(description = "用户喜好设置")
public class XryRecommendController extends AbstractController {
    @Resource
    private XryCourseCatService xryCourseCatService;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private ShiroService shiroService;

    /**
     * 所有类目查询
     * @return
     */
    @GetMapping("/appListCourseCat")
    @ApiOperation(value="所有类目查询",notes="不需要任何参数")
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
    @PostMapping("/appInsertRecommendCourseCat")
    @ApiOperation(value="用户设置喜好课程，添加到数据库表中",notes="tokenJSON：用户选择好的喜好（类目）；request：请求头里待token")
    public Result appInsertRecommendCourseCat(@RequestBody String tokenJSON, HttpServletRequest request){
        JSONObject tokenJSONObject = new JSONObject(tokenJSON);
        JSONArray catList = (JSONArray) tokenJSONObject.get("catGroup");
        // 判断用户是添加还是修改
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            } else {
                XryUserEntity user = shiroService.queryUsers(tokenEntity.getUserId());
                Map<String,Object> params = new HashMap<>();
                params.put("userId",user.getId());
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
            }
        }
        return Result.ok();
    }

    /**
     * 查询用户已经设置的喜好（类目）
     * @param request
     * @return
     */
    @PostMapping("/listRecommendCourseCatByUserId")
    @ApiOperation(value="查询用户已经设置的喜好（类目）",notes="request：请求头里待token")
    public Result listRecommendCourseCatByUserId(HttpServletRequest request){
        String accessToken = request.getHeader("token");
        List<Map<String, Object>> catList3 = null;
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            } else {
                XryUserEntity user = shiroService.queryUsers(tokenEntity.getUserId());
                String courseCat = xryCourseCatService.listRecommendCourseCatByUserId(user.getId());
                List<Long> catIdList = new ArrayList<>();
                if (StringUtils.isNotBlank(courseCat)) {
                    String catId = courseCat.split("\\[")[1];
                    catId = catId.split("\\]")[0];
                    if (StringUtils.isNotBlank(catId)) {
                        String[] catIdArr = catId.split(",");
                        for (String id : catIdArr) {
                            catIdList.add(Long.valueOf(id));
                        }
                    }
                }
                // 查询出所有类目list
                List<Map<String, Object>> catList = xryCourseCatService.listCourseCat();
                if (null != catList) {
                    List<Map<String, Object>> catMapList2 = new ArrayList<>();
                    for (Map<String, Object> subMap : catList) {
                        Long courseCatId = (Long) subMap.get("id");
                        int checks = (int) subMap.get("checks");
                        if (catIdList.contains(courseCatId)) {
                            checks = 1;
                        }
                        // 放回子类目的map里去
                        subMap.put("checks", checks);
                        catMapList2.add(subMap);
                    }
                    catList3 = ListUtil.listToTreeList(catMapList2,"id","parent_id","catList");
                }
            }
        }
        return Result.ok().put("catList", catList3);
    }

}
