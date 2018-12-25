package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: APP首頁内容
 * @author: YJmiss
 * @create: 2018-11-29 13:51
 **/
@RestController
@RequestMapping("/api/appContent")
@Api(description = "APP首頁api")
public class AppContentController extends AbstractController {
    @Resource
    private XryContentService contentService;
    @Resource
    private XryCourseService courseService;
    @Resource
    private XryArticleService articleService;
    @Resource
    private XryMessageService xryMessageService;
    @Resource
    private ShiroService shiroService;
    
    /**
     * @Description: 首页轮播，中部广告信息
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/11/29
     */
    @GetMapping("/advertising/list")
    public Result getContentList() {
        List<XryContentEntity> contentsByCat1 = contentService.getContentsByCat(1);
        List<XryContentEntity> contentsByCat2 = contentService.getContentsByCat(2);
        List<XryContentEntity> contentsByCat3 = contentService.getContentsByCat(3);

        Map<String, Object> map = new HashMap<>();
        map.put("Advertising1", contentsByCat1);
        map.put("Advertising2", contentsByCat2);
        map.put("Advertising3", contentsByCat3);
        return Result.ok(map);
    }

    /**
     * @Description: 获取首页好评好课
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/11/29
     */
    @GetMapping("/goodCourse/list")
    public Result getGoodCourse() {
        List<XryGoodCourseEntity> goodCourse = courseService.getGoodCourse();
        Map<String, Object> map = new HashMap<>();
        map.put("GoodCourse", goodCourse);
        return Result.ok(map);
    }

    /**
     * @Description: 获取分类页面推荐文章
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/20
     */
    @GetMapping("/recommendArticle/list")
    public Result recommendArticle() {
        List<XryArticleEntity> articles = articleService.getrecommendArticle();
        return Result.ok().put("recommendArticle", articles);
    }

    /**
     * 首页右上角未读消息数量查询
     * @return
     */
    @GetMapping("/message/countIndexMessage")
    @ApiOperation(value = "首页右上角未读消息数量查询", notes = "用户不登录的情况下，显示平台消息")
    public Result countIndexMessage(HttpServletRequest request) throws Exception {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        Integer courseMessageCount = 0;
        Integer teacherMessageCount = 0;
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            //token失效
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
            params.put("userId", userId);
            // 1、查询用户课程未读消息数（课程消息、我关注的、平台消息）
            courseMessageCount = xryMessageService.countCourseMessageByUserId(params);
            teacherMessageCount = xryMessageService.countTeacherMessageByUserId(params);
        }
        Integer systemMessageCount = xryMessageService.countSystemMessage();
        // 2、未读消息总数
        Integer messageCount = courseMessageCount + teacherMessageCount + systemMessageCount;
        return Result.ok().put("messageSum", messageCount);
    }
    
    /**
     * 进入消息中心->课程消息列表、课程未读消息
     * @return
     */
    @GetMapping("/message/queryCourseMessageListAndCount")
    @ApiOperation(value = "进入消息中心查询已读和未读消息列表和消息数", notes = "默认显示课程消息列表和课程消息未读数")
    public Result queryCourseMessageListAndCount(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        Integer courseMessageCount = 0;
        Integer teacherMessageCount = 0;
        List<Map<String, Object>> courseMessageList = null;

        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            //token失效
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
            params.put("userId", userId);
            // 1、查询用户课程未读消息数（课程消息、我关注的、平台消息），可传参，也可再次查询
            courseMessageCount = xryMessageService.countCourseMessageByUserId(params);
            teacherMessageCount = xryMessageService.countTeacherMessageByUserId(params);
            // 2、查询用户课程消息列表，包括已读和未读
            courseMessageList = xryMessageService.listCourseMessageByUserId(params);
        }
        Integer systemMessageCount = xryMessageService.countSystemMessage();
        List<Map<String, Object>> messageList = new ArrayList<>();
        Map<String, Object> courseMessageMap = new HashMap<>();
        courseMessageMap.put("messageTitle", "课程消息");
        courseMessageMap.put("messageCount", courseMessageCount);
        courseMessageMap.put("courseMessageList", courseMessageList);
        messageList.add(courseMessageMap);

        Map<String, Object> teacherMessageMap = new HashMap<>();
        teacherMessageMap.put("messageTitle", "我关注的");
        teacherMessageMap.put("messageCount", teacherMessageCount);
        messageList.add(teacherMessageMap);

        Map<String, Object> systemMessageMap = new HashMap<>();
        systemMessageMap.put("messageTitle", "平台消息");
        systemMessageMap.put("messageCount", systemMessageCount);
        messageList.add(systemMessageMap);
        return Result.ok().put("messageList", messageList);
    }

    /**
     * 进入消息中心->讲师消息列表
     * @return
     */
    @GetMapping("/message/queryTeacherMessageListAndCount")
    @ApiOperation(value = "进入消息中心查询讲师消息列表", notes = "包括已读消息列表和未读消息列表")
    public Result queryTeacherMessageListAndCount(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            //token失效
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        params.put("userId", userId);
        Map<String, Object> map = new HashMap<>();
        // 查询用户讲师消息列表，包括已读和未读
        List<Map<String, Object>> teacherMessageList = xryMessageService.listTeacherMessageByUserId(params);
        map.put("teacherMessageList", teacherMessageList);
        return Result.ok(map);
    }

    /**
     * 进入消息中心->平台消息列表
     * @return
     */
    @GetMapping("/message/querySystemMessageListAndCount")
    @ApiOperation(value = "平台消息列表", notes = "包括已读消息列表和未读消息列表")
    public Result querySystemMessageListAndCount() {
        Map<String, Object> map = new HashMap<>();
        // 查询平台消息列表
        List<Map<String, Object>> systemMessageList = xryMessageService.listSystemMessage();
        map.put("systemMessageList", systemMessageList);
        return Result.ok(map);
    }

    /**
     * 用户读消息
     * @param objId
     * @param flag
     * @return
     */
    @GetMapping("/message/ReadMessage")
    @ApiOperation(value = "用户读消息，根据消息id", notes = "objId：记录用户已读消息、未读消息的对象id，必填；flag：标识符，必填")
    public Result ReadMessage(@RequestParam String objId, Integer flag, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            //token失效
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        params.put("flag", flag);
        params.put("objId", objId);
        params.put("userId", userId);
        return Result.ok();
    }
}
