package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.exception.GlobalException;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import com.oservice.admin.modules.app.service.OrderCourseService;
import com.oservice.admin.modules.app.service.OrderService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryUserApplicantService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    /** 课程加入学习的标识符 */
    final static Integer COURSE_JOIN_STUDY = 1;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private XryUserApplicantService xryUserApplicantService;   // 报名表
    @Resource
    private OrderService orderService;
    @Resource
    private OrderCourseService orderCourseService;

    /**
     * app端用户加入课程学习
     *
     * @param courseId 课程id
     * @param isSelect 是否加入学习
     * @return
     */
    @SysLog("app端用户加入课程学习")
    @PostMapping("/appJoinCourseStudy")
    public Result appJoinCourseStudy(@RequestParam Long courseId, boolean isSelect) {
        // 从token中获取登录人信息
        // 把课程id和用户id加入到数据库表中
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getAppUserId());
        params.put("courseId", courseId);
        params.put("type", COURSE_JOIN_STUDY);
        Integer isSuccess = xryUserApplicantService.appSaveCourse(params);
        if (1 == isSuccess) {
            // 给课程计数+1
            xryCourseService.updateCourseApplicationCount(courseId, 1);
            return Result.ok().put("1", "课程加入学习成功");
        } else {
            return Result.error(2, "课程加入学习失败");
        }
    }

    /**
     * app端根据用户查询用户加入学习的课程列表
     * @param pageNo
     * @param pageSize
     * @param flag 标识符（本周学习、本月学习，全部课程）
     * @return
     */
    @SysLog("app端根据用户查询用户加入学习的课程列表")
    @GetMapping("/appPageListCourseByUserId")
    public Result appPageListCourseByUserId(@RequestParam Integer pageNo, Integer pageSize, Integer flag) {
        // 把课程id和用户id加入到数据库表中
        Map<String, Object> params = new HashMap<>();
        params.put("userId", getAppUserId());
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("flag", flag);
        PageUtils page = xryUserApplicantService.appPageListCourseByUserId(params);
        return Result.ok().put("page", page);
    }

    /**
     * app端用户删除已经加入学习的课程
     *
     * @param ids
     * @return
     */
    @SysLog("app端用户删除已经加入学习的课程")
    @PostMapping("/appDelCourseById")
    public Result appDelCourseById(@RequestParam Long[] ids) {
        Map<String, Object> params = new HashMap<>();
        params.put("ids", ids);
        params.put("userId", getAppUserId());
        xryUserApplicantService.appDelCourseById(params);
        return Result.ok();
    }

    /**
     * app端课程详情查询
     *
     * @param courseId
     * @return
     */
    @SysLog("app端课程详情查询")
    @GetMapping("/appQueryCourseDetailByCourseId")
    public Result appQueryCourseDetailByCourseId(@RequestParam Long courseId) {
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
        try {
            String id = null;
            if (id == null || id.equals("")) {
                detail.put("identifying", false);
            } else {
                List<String> orderId = orderService.getOrderIdByUserId(id);
                if (orderId.size() > 0) {
                    List<Long> courseIds = new ArrayList<>();
                    for (String order : orderId) {
                        List<XryOrderCourseEntity> orderCourses = orderCourseService.getOrderCourses(order);
                        for (XryOrderCourseEntity courses : orderCourses) {
                            long courseId1 = courses.getCourseId();
                            courseIds.add(courseId1);
                        }
                    }
                    if (courseIds.contains(courseId)) {
                        detail.put("identifying", true);
                    } else {
                        detail.put("identifying", false);
                    }
                } else {
                    detail.put("identifying", false);
                }
            }

        } catch (GlobalException e) {
            detail.put("identifying", false);
        }
        return Result.ok(detail);
    }

    /**
     * app端课程详情评价查询
     *
     * @param courseId
     * @return
     */
    @SysLog("app端课程详情评价查询")
    @GetMapping("/appQueryCourseCommentByCourseId")
    public Result appQueryCourseCommentByCourseId(@RequestParam Long courseId, Integer pageNo, Integer pageSize) {
        Map<String, Object> detail = new HashMap<>();
        // 3、查询课程"评价"
        Map<String, Object> courseCommentList = xryCourseService.listCourseCommentByCourseId(courseId, pageNo, pageSize);
        detail.put("courseCommentList", courseCommentList);
        return Result.ok(detail);
    }

    /**
     * app端相关课程查询
     *
     * @param courseId
     * @return
     */
    @SysLog("app端相关课程查询")
    @GetMapping("/appQuerySimilarityCourseByCourseId")
    public Result appQuerySimilarityCourseByCourseId(@RequestParam Long courseId) {
        Map<String, Object> detail = new HashMap<>();
        // 4、查询"相关课程"
        Map<String, Object> relatedCourseList = xryCourseService.listRelatedCourseByCourseId(courseId);
        detail.put("relatedCourseList", relatedCourseList);
        return Result.ok(detail);
    }

    /**
     * app端课程中心接口
     *
     * @param params
     * @return
     */
    @SysLog("app端课程中心接口")
    @GetMapping("/appListCourseCenter")
    public Result appListCourseCenter(@RequestParam String params) {
        List<Map<String, Object>> courseList = xryCourseService.appListCourseCenter(params);
        return Result.ok().put("courseList",courseList);
    }

}
