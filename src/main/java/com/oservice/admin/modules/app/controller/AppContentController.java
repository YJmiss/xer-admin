package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryContentService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

        Map<String, Object> map = new HashMap<>();
        map.put("Advertising1", contentsByCat1);
        map.put("Advertising2", contentsByCat2);
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
        List<XryCourseEntity> goodCourse = courseService.getGoodCourse();
        Map<String, Object> map = new HashMap<>();
        map.put("GoodCourse", goodCourse);
        return Result.ok(map);
    }
}
