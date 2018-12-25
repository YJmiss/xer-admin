package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.exception.GlobalException;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.app.entity.XryOrderCourseEntity;
import com.oservice.admin.modules.app.service.OrderCourseService;
import com.oservice.admin.modules.app.service.OrderService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryUserApplicantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
@Api(description = "APP课程控制器")
public class AppCourseController extends AbstractController {
    /** 课程加入学习的标识符 */
    final static Integer COURSE_JOIN_STUDY = 0;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private XryUserApplicantService xryUserApplicantService;   // 报名表
    @Resource
    private OrderService orderService;
    @Resource
    private OrderCourseService orderCourseService;
    @Resource
    private ShiroService shiroService;
    @Resource
    private SolrJDao solrJDao;
    /**
     * app端用户加入课程学习
     *
     * @param courseId 课程id
     * @return
     */
    @SysLog("app端用户加入课程学习")
    @PostMapping("/appCourseApplicantByCourseId")
    @ApiOperation(value="用户报名学习课程",notes="courseId是加入学习的课程id，必填")
    public Result appCourseApplicantByCourseId(@RequestParam Long courseId, HttpServletRequest request) {
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
        params.put("objId", courseId);
        params.put("objType", COURSE_JOIN_STUDY);
        Integer isSuccess = xryUserApplicantService.appSaveCourse(params);
        if (1 == isSuccess) {
            // 给课程计数+1
            xryCourseService.updateCourseApplicationCount(courseId, 1);
            //TODO:后续测试用户在加入学习的时候更新solr索引人气字段
            XryCourseEntity course = xryCourseService.queryById(courseId);
            //获取评论百分数
            Integer feedback = xryCourseService.getFeedback(courseId);
            try {
                solrJDao.update(courseId,feedback,0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
            //得到课程的学习数，
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
     * app端根据用户查询用户加入学习的课程列表
     * @param pageNo
     * @param pageSize
     * @param flag 标识符（本周学习、本月学习，全部课程）
     * @return
     */
    @SysLog("app端根据用户查询用户加入学习的课程列表")
    @PostMapping("/appPageListCourseByUserId")
    @ApiOperation(value="查询用户已报名学习课程列表",notes="pageNo：页码，必填；pageSize：单页的列表数量，必填；flag：标识符，用来标识本周学习、本月学习、全部课程，必填")
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
    @ApiOperation(value="用户取消已报名学习课程",notes="ids：是取消学习的课程id，Long数组（批量操作课程id），必填")
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
    @PostMapping("/appQueryCourseDetailByCourseId")
    @ApiOperation(value="课程详情->课程详细信息",notes="courseId：是课程id，必填")
    public Result appQueryCourseDetailByCourseId(@RequestParam Long courseId, HttpServletRequest request) {
        if (null == courseId) {
            return Result.error(1, "查询出错");
        }
        Map<String, Object> detail = new HashMap<>();
        // 查询"课程详情"
        Map<String, Object> courseDetailContent = xryCourseService.queryCourseDetailByCourseId(courseId);
        detail.put("courseDetailContent", courseDetailContent);
        try {
            String accessToken = request.getHeader("token");
            String userId = "";
            if (StringUtils.isNotBlank(accessToken)) {
                SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
                //token失效
                if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                    return Result.error(204, "token失效，请重新登录");
                    //throw new IncorrectCredentialsException("token失效，请重新登录");
                }
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
            }
            if (userId == null || userId.equals("")) {
                detail.put("identifying", false);
            } else {
                List<String> orderId = orderService.getOrderIdByUserId(userId);
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
     * app端课程目录查询
     *
     * @param courseId
     * @return
     */
    @SysLog("app端课程目录查询")
    @PostMapping("/appListCourseCatalogByCourseId")
    @ApiOperation(value="课程详情->课程所有目录列表",notes="courseId：是课程id，必填")
    public Result appListCourseCatalogByCourseId(@RequestParam Long courseId) {
        // 查询课程"目录"
        Map<String, Object> courseCatalogList = xryCourseService.listCourseCatalogByCourseId(courseId);
        return Result.ok(courseCatalogList);
    }

    /**
     * app端课程详情评价查询
     *
     * @param courseId
     * @return
     */
    @SysLog("app端课程详情评价查询")
    @PostMapping("/appQueryCourseCommentByCourseId")
    @ApiOperation(value="课程详情->课程评论列表",notes="courseId：是课程id，必填；pageNo：页码，必填；pageSize：单页的列表数量，必填")
    public Result appQueryCourseCommentByCourseId(@RequestParam Long courseId, Integer pageNo, Integer pageSize) {
        // 查询课程"评价"
        Map<String, Object> courseCommentList = xryCourseService.listCourseCommentByCourseId(courseId, pageNo, pageSize);
        return Result.ok(courseCommentList);
    }

    /**
     * app端相关课程查询
     *
     * @param courseId
     * @return
     */
    @SysLog("app端相关课程查询")
    @PostMapping("/appQuerySimilarityCourseByCourseId")
    @ApiOperation(value="课程详情->相关课程列表",notes="courseId：是课程id，必填")
    public Result appQuerySimilarityCourseByCourseId(@RequestParam Long courseId) {
        // 查询"相关课程"
        Map<String, Object> relatedCourseList = xryCourseService.listRelatedCourseByCourseId(courseId);
        return Result.ok(relatedCourseList);
    }

    /**
     * app端课程中心接口
     *
     * @param params
     * @return
     */
    @SysLog("app端课程中心接口")
    @GetMapping("/appListCourseCenter")
    @ApiOperation(value="课程中心分类筛选接口",notes="params：分类信息json对象，params包含筛选所必须的参数，必填")
    public Result appListCourseCenter(@RequestParam String params) {
        List<Map<String, Object>> courseList = xryCourseService.appListCourseCenter(params);
        return Result.ok().put("courseList",courseList);
    }

}
