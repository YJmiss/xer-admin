package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.XryArticleEntity;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.entity.XryGoodCourseEntity;
import com.oservice.admin.modules.sys.service.XryArticleService;
import com.oservice.admin.modules.sys.service.XryContentService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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
     * 首页右上角消息数量查询
     * @return
     */
    @GetMapping("/Message/countMessage")
    @ApiOperation(value = "首页右上角消息数量查询", notes = "flag：记录用户已读消息、未读消息的标识符")
    public Result countMessage(@RequestParam Integer flag) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getAppUserId());
        params.put("flag", 0);
        Integer messageCount = xryMessageService.countMessageByUserId(params);

        return Result.ok();
    }
}
