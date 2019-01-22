package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.ListUtil;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

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
    @Resource
    private XryCourseCatService xryCourseCatService;

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
     *
     * @return
     */
    @GetMapping("/message/countIndexMessage")
    @ApiOperation(value = "首页右上角未读消息数量查询，不需要有效的token（token可为空）", notes = "用户不登录的情况下，显示平台消息")
    public Result countIndexMessage(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        Integer courseMessageCount = 0;
        Integer teacherMessageCount = 0;
        Integer systemMessageCount = 0;
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                // 没有登录的情况下，查询所有的平台消息
//                systemMessageCount = xryMessageService.countSystemMessage();
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
                params.put("userId", userId);
                // 1、查询用户课程未读消息数（课程消息、我关注的、平台消息）
                courseMessageCount = xryMessageService.countCourseMessageByUserId(params);
                teacherMessageCount = xryMessageService.countTeacherMessageByUserId(params);
                // 根据userId查询删除记录表，集合返回msg_id
                List<Long> msgIdList = xryMessageService.listMsgIdByUserId(userId);
                // 登录的情况下，查询用户没有删除的平台消息，并且是未读数量
                systemMessageCount = xryMessageService.countSystemMessageByUserId(userId, msgIdList);
                if (null == systemMessageCount) systemMessageCount = 0;
            }
        }

        // 2、未读消息总数
        Integer messageCount = courseMessageCount + teacherMessageCount + systemMessageCount;
        return Result.ok().put("messageSum", messageCount);
    }

    /**
     * 进入消息中心->课程消息列表、课程未读消息
     *
     * @return
     */
    @GetMapping("/message/queryCourseMessageListAndCount")
    @ApiOperation(value = "进入消息中心查询已读和未读消息列表和消息数", notes = "默认显示课程消息列表和课程消息未读数")
    public Result queryCourseMessageListAndCount(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        Integer courseMessageCount = 0;
        Integer teacherMessageCount = 0;
        Integer systemMessageCount = 0;
        List<Map<String, Object>> systemMessageList = new ArrayList<>();
        List<Map<String, Object>> courseMessageList = null;
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                // 没有登录的情况下，查询所有的平台消息
//                systemMessageCount = xryMessageService.countSystemMessage();
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
                params.put("userId", userId);
                // 1、查询用户课程未读消息数（课程消息、我关注的、平台消息），可传参，也可再次查询
                courseMessageCount = xryMessageService.countCourseMessageByUserId(params);
                teacherMessageCount = xryMessageService.countTeacherMessageByUserId(params);
                // 2、查询用户课程消息列表，包括已读和未读
                courseMessageList = xryMessageService.listCourseMessageByUserId(params);
                // 根据userId查询删除记录表，集合返回msg_id
                List<Long> msgIdList = xryMessageService.listMsgIdByUserId(userId);
                // 登录的情况下，查询用户没有删除的平台消息，并且是未读数量
                systemMessageCount = xryMessageService.countSystemMessageByUserId(userId, msgIdList);
                if (null == systemMessageCount) systemMessageCount = 0;
            }
        }
        List<Map<String, Object>> messageList = new ArrayList<>();
        Map<String, Object> courseMessageMap = new HashMap<>();
        courseMessageMap.put("messageTitle", "课程消息");
        courseMessageMap.put("messageCount", courseMessageCount);
        courseMessageMap.put("courseMessageList", courseMessageList);
        messageList.add(courseMessageMap);

        Map<String, Object> teacherMessageMap = new HashMap<>();
        teacherMessageMap.put("messageTitle", "我关注的");
        teacherMessageMap.put("messageCount", teacherMessageCount);
        List<Map<String, Object>> teacherMessageList = new ArrayList<>();
        teacherMessageMap.put("teacherMessageList", teacherMessageList);
        messageList.add(teacherMessageMap);

        Map<String, Object> systemMessageMap = new HashMap<>();
        systemMessageMap.put("messageTitle", "平台消息");
        systemMessageMap.put("messageCount", systemMessageCount);
        systemMessageMap.put("systemMessageList", systemMessageList);
        messageList.add(systemMessageMap);
        return Result.ok().put("messageList", messageList);
    }

    /**
     * 进入消息中心->讲师消息列表
     *
     * @return
     */
    @GetMapping("/message/queryTeacherMessageListAndCount")
    @ApiOperation(value = "进入消息中心查询讲师消息列表", notes = "包括已读消息列表和未读消息列表")
    public Result queryTeacherMessageListAndCount(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        List<Map<String, Object>> teacherMessageList = null;
        Integer teacherMessageCount = 0;
        String accessToken = request.getHeader("token");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
//                return Result.error(204, "token失效，请重新登录");
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
                params.put("userId", userId);
                // 查询用户讲师消息列表，包括已读和未读
                teacherMessageList = xryMessageService.listTeacherMessageByUserId(params);
            }
        }
        map.put("teacherMessageList", teacherMessageList);
        if (teacherMessageList.size() > 0) {
            teacherMessageCount = teacherMessageList.size();
        }
        map.put("teacherMessageCount", teacherMessageCount);
        return Result.ok(map);
    }

    /**
     * 进入消息中心->平台消息列表
     *
     * @return
     */
    @GetMapping("/message/querySystemMessageListAndCount")
    @ApiOperation(value = "平台消息列表", notes = "包括已读消息列表和未读消息列表")
    public Result querySystemMessageListAndCount(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Integer systemMessageCount = 0;
        List<Map<String, Object>> systemMessageList = new ArrayList<>();
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                // 没有登录的情况下，查询所有的平台消息
//                systemMessageList = xryMessageService.listSystemMessage();
//                systemMessageCount = xryMessageService.countSystemMessage();
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                String userId = users.getId();
                // 根据userId查询删除记录表，集合返回msg_id
                List<Long> msgIdList = xryMessageService.listMsgIdByUserId(userId);
                // 查询用户没有删除的消息列表
                systemMessageList = xryMessageService.listSystemMessageByUserId(userId, msgIdList);
                // 登录的情况下，查询用户没有删除的平台消息，并且是未读数量
                systemMessageCount = xryMessageService.countSystemMessageByUserId(userId, msgIdList);
                if (null == systemMessageCount) systemMessageCount = 0;
            }
        }
        map.put("systemMessageList", systemMessageList);
        map.put("systemMessageCount", systemMessageCount);
        return Result.ok(map);
    }

    /**
     * 用户读消息
     *
     * @param messageId
     * @param flag
     * @return
     */
    @GetMapping("/message/readUserMessageByUserId")
    @ApiOperation(value = "用户读消息，根据消息id", notes = "messageId：记录用户已读消息、未读消息的id，必填；flag：标识符，标识课程消息1、我的关注2、平台消息3，必填")
    public Result readUserMessageByUserId(@RequestParam Long messageId, Integer flag, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        if (1 == flag || 2 == flag) {
            // 课程消息、我的关注，需要有效的token
            String accessToken = request.getHeader("token");
            if (StringUtils.isNotBlank(accessToken)) {
                SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
                if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                    return Result.error(204, "token失效，请重新登录");
                }
            }
        }
        params.put("messageId", messageId);
        // 查询消息对象信息返回（如有需要）
        Map<String, Object> message = xryMessageService.readUserMessageByUserId(params);
        // 读取消息后，把置为已读状态（字段read_status设为1）
        xryMessageService.updateReadStatusByMessageId(messageId);
        return Result.ok().put("message", message);
    }

    /**
     * 用户读消息
     *
     * @param messageId
     * @return
     */
    @GetMapping("/message/delUserMessageByUserId")
    @ApiOperation(value = "用户删除消息，根据消息id", notes = "messageId：记录用户已读消息、未读消息的id，必填；flag：标识符，标识课程消息1、我的关注2、平台消息3，必填")
    public Result delUserMessageByUserId(@RequestParam Long messageId, HttpServletRequest request) {
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
                xryMessageService.addDelMsgByUserIdAndMsgId(messageId, userId);
            }
        } else {
            return Result.error(203, "token为空，请重新登录");
        }
        return Result.ok();
    }

    @PostMapping("/listCourseByRecommendCatAndByUserId")
    @ApiOperation(value = "查询用户已经设置的喜好（类目）下的课程列表", notes = "pageNo：页码；pageSize：单页显示列表条数；request：请求头里待token")
    public Result listCourseByRecommendCatAndByUserId(@RequestParam Integer pageNo, @RequestParam Integer pageSize, HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        List<Map<String, Object>> courseRecommendList = null;
        List<Map<String, Object>> userSettingCourseList = null;
        Map<String, Object> courseList = new HashMap();
        Map<String, Object> params = new HashMap();
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {

            } else {
                // 登录的情况下，查询用户的喜好设置类目
                // 每个类目下查询6个课程，数量不足的查询管理员设置的推荐课程补上
                XryUserEntity user = shiroService.queryUsers(tokenEntity.getUserId());
                String courseCatIdArr = xryCourseCatService.listRecommendCourseCatByUserId(user.getId());
                List<String> catIdList = new ArrayList<>();
                if (StringUtils.isNotBlank(courseCatIdArr)) {
                    String catId = courseCatIdArr.split("\\[")[1];
                    catId = catId.split("\\]")[0];
                    if (StringUtils.isNotBlank(catId)) {
                        String[] catIdArr = catId.split(",");
                        for (String id : catIdArr) {
                            catIdList.add(id);
                        }
                    }
                }
                List<Map<String, Object>> courseCatList = new ArrayList<>();
                Map<String, Object> courseCat = null;
                for (String catId : catIdList) {
                    Long courseCatId = Long.valueOf(catId);
                    // 根据类目id查询出类目信息（类目标题）
                    courseCat = xryCourseCatService.getCourseCatById(courseCatId);
                    courseCatList.add(courseCat);
                    params.put("courseCatId", courseCatId);
                    // 根据类目id查询出喜好设置的课程
                    userSettingCourseList = courseService.listUserSettingCourseByUserId(params);
                    courseCat.put("userSettingCourseList", userSettingCourseList);
                }
                courseList.put("courseCatList",courseCatList);
            }
        }
        // 没有登录的情况下，查询管理员根据类目设置的推荐课程
        // 查询全部，返回一个list，不根据类目分类
        courseRecommendList = courseService.listRecommendCourse(params);
        courseList.put("courseRecommendList", courseRecommendList);
        return Result.ok().put("courseList", courseList);
    }

}
