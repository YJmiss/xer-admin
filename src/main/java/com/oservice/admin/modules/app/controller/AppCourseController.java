package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统用户
 * app端课程的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appCourse")
public class AppCourseController extends AbstractController {
    @Resource
    private XryCourseService xryCourseService;

    /**
     * app端课程详情查询
     * @param courseId
     * @return
     */
    @SysLog("app端课程详情查询")
    @GetMapping("/appQueryCourseDetailByCourseId")
    public Result appQueryCourseDetailByCourseId(@RequestParam Long courseId, Integer pageNo, Integer pageSize) {
        if (null == courseId) {
            return Result.error(1, "查询出错");
        }
        Map<String, Object> detail = new HashMap<>();
        // 1、查询"课程详情"
        Map<String, Object> courseDetailContent = xryCourseService.queryCourseDetailByCourseId(courseId);
        detail.put("courseDetailContent", courseDetailContent);
        // 2、查询课程"目录"
        Map<String, Object> courseCatalogList = xryCourseService.listCourseCatalogByCourseId(courseId);
        detail.put("courseCatalogList", courseCatalogList);
        // 3、查询课程"评价"
        Map<String, Object> courseCommentList = xryCourseService.listCourseCommentByCourseId(courseId, pageNo, pageSize);
        detail.put("courseCommentList", courseCommentList);
        // 4、查询"相关课程"
        Map<String, Object> relatedCourseList = xryCourseService.listRelatedCourseByCourseId(courseId);
        detail.put("relatedCourseList", relatedCourseList);
        // 5、转成json字符串
        //JSONObject courseDetail = new JSONObject(detail);
        return Result.ok(detail);
    }


}
