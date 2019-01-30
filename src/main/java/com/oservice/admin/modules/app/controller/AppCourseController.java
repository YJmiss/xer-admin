package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.exception.GlobalException;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.app.entity.AppCartEntity;
import com.oservice.admin.modules.app.entity.CourseClick;
import com.oservice.admin.modules.app.service.*;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
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
    @Resource
    private RecordtimeService recordtimeService;
    @Resource
    private XryUserCollectService xryUserCollectService;
    @Resource
    private XryUserAttentionService xryUserAttentionService;
    @Resource
    private CartService cartService;
    @Resource
    private CourseClickService courseClickService;

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
        // 校验该用户是否已经报名学习
        XryUserApplicantEntity isApplicant = xryUserApplicantService.isApplicantByUserIdAndCourseId(courseId, userId);
        if (null != isApplicant) {
            return Result.error(3, "该用户已经报名学习，不能再次报名");
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
           /*  Integer feedback = xryCourseService.getFeedback(courseId);
            try {
                solrJDao.update(courseId,course.getApplicantCount(),0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }*/
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
    public Result appPageListCourseByUserId(@RequestParam Integer pageNo, @RequestParam Integer pageSize, @RequestParam Integer flag, HttpServletRequest request) {
        // 把课程id和用户id加入到数据库表中
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        params.put("userId", userId);
        params.put("pageNo", (pageNo - 1) * pageSize);
        params.put("pageSize", pageSize);
        params.put("flag", flag);
        PageUtils page = xryUserApplicantService.appPageListCourseByUserId(params);
        return Result.ok().put("page", page);
    }

    /**
     * app端用户删除已经加入学习的课程
     *
     * @param id
     * @return
     */
    @SysLog("app端用户删除已经加入学习的课程")
    @PostMapping("/appDelCourseById")
    @ApiOperation(value="app端用户删除已经加入学习的课程",notes="id：是取消学习的报名表id，必填")
    public Result appDelCourseById(@RequestParam Long id, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            userId = users.getId();
        }
        params.put("id", id);
        params.put("userId", userId);
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
        if (null == courseId) return Result.error(1, "查询出错"); 
        Map<String, Object> detail = new HashMap<>();
        // 查询"课程详情"
        Map<String, Object> courseDetailContent = xryCourseService.queryCourseDetailByCourseId(courseId);
        Map<String, Object> detailContent = (Map<String, Object>) courseDetailContent.get("detailContent");
        Long price = (Long) detailContent.get("price");
        detailContent.put("appPrice", new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString());
        detail.put("courseDetailContent", courseDetailContent);
        Integer isApplicant = 0;
        Integer isCollect = 0;
        Integer isAttention = 0;
        Integer isCart = 0;
        try {
            String accessToken = request.getHeader("token");
            String userId = "";
            // 查询课程的报名人数列表
            List<XryUserApplicantEntity> courseApplicantList = xryUserApplicantService.countApplicantByCourseId(courseId);
            Integer courseApplicantCount = courseApplicantList.size();
            detail.put("courseApplicantCount", courseApplicantCount);
            if (StringUtils.isNotBlank(accessToken)) {
                SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
                if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                    isApplicant = 0;
                    isCollect = 0;
                    isAttention = 0;
                    isCart = 0;
                } else {
                    XryUserEntity user = shiroService.queryUsers(tokenEntity.getUserId());
                    userId = user.getId();
                    if (courseApplicantCount > 0) {
                        // 根据用户id和课程id查询该用户是否报名了该课程
                        XryUserApplicantEntity userApplicant = xryUserApplicantService.isApplicantByUserIdAndCourseId(courseId, userId);
                        if (null != userApplicant) {
                            isApplicant = 1;
                        }
                    }
                    Map<String, Object> collect = xryUserCollectService.isCollectByUserIdAndObjId(courseId.toString(), userId);
                    if (collect == null || collect.size() < 1) {
                        isCollect = 0;
                    } else if (collect.size() > 0) {
                        detail.put("collect", collect);
                        isCollect = 1;
                    }
                    Map<String, Object> teacher = (Map<String, Object>) courseDetailContent.get("teacher");
                    String tid = (String) teacher.get("id");
                    XryUserAttentionEntity attention = xryUserAttentionService.isAttentionByUserIdAndTeacherId(tid, userId);
                    if (attention != null) {
                        detail.put("attention", attention);
                        isAttention = 1;
                    }
                    // 查询该课程是否被用户接入到购物车
                    List<AppCartEntity> cartList = cartService.getCartListFromRedis(user);
                    if (null != cartList) {
                        if (cartList.size() > 0) {
                            isCart = 1;
                        }
                    }
                }
            }
        } catch (GlobalException e) {
           e.printStackTrace();
        }
        detail.put("isApplicant", isApplicant);
        detail.put("isCollect", isCollect);
        detail.put("isAttention", isAttention);
        detail.put("isCart", isCart);
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
    public Result appListCourseCatalogByCourseId(@RequestParam Long courseId, HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        String userId = "";
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            //token失效
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
//                return Result.error(204, "token失效，请重新登录");
//                throw new IncorrectCredentialsException("token失效，请重新登录");
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
            }
        }
        if (userId == null || userId.equals("")) {
            // 查询课程"目录"
            Map<String, Object> courseCatalogList = xryCourseService.listCourseCatalogByCourseId(courseId);
            return Result.ok(courseCatalogList);
        } else {
            Map<String, Object> courseCatalogList = xryCourseService.listCourseCatalogByCourseIdAndUsherId(courseId, userId);
            return Result.ok(courseCatalogList);
        }

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
    public Result appQueryCourseCommentByCourseId(@RequestParam Long courseId, Integer pageNo, Integer pageSize, HttpServletRequest request) {
        // 查询课程"评价"
        String userId = "";
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {

            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                userId = users.getId();
            }
        }
        Map<String, Object> courseCommentList = xryCourseService.listCourseCommentByCourseId(courseId, pageNo, pageSize, userId);
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

    //long videoId,long studyTime,long sumTime
    @SysLog("app用户查看课程视频学习时间")
    @PostMapping("/queryPlayCourseTime")
    @ApiOperation(value = "app用户查看课程视频学习时间", notes = "查看用户购买课程的ID")
    public Result queryPlayCourseTime(@RequestParam long courseId) {
        Map<String, Object> map = recordtimeService.queryPlayCourseTimeByUserIdAndCourseId(courseId, getAppUserId());
        return Result.ok(map);
    }

    @SysLog("app保存用户学习时间")
    @PostMapping("/addStudyTime")
    @ApiOperation(value = "app用户保存视频学习时间", notes = "保存用户时间")
    public Result addStudyTime(@RequestParam long courseId, long videoId, long studyTime, long sumTime) {
        recordtimeService.addStudyTime(courseId, videoId, studyTime, sumTime, getAppUserId());
        return Result.ok();
    }

    @SysLog("app保存用户课程学习进度")
    @PostMapping("/addCourseStudyProgress")
    @ApiOperation(value = "app保存用户课程学习进度", notes = "courseId：课程id；studyProgress：学习进度；需要在请求头里待token")
    public Result addCourseStudyProgress(@RequestParam String courseId, Integer studyProgress, HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        Integer isLogin = 0;
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity != null || tokenEntity.getExpireTime().getTime() > System.currentTimeMillis()) {
                isLogin = 1;
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                String userId = users.getId();
                Map<String, Object> params = new HashMap<>();
                params.put("userId", userId);
                params.put("courseId",Long.parseLong(courseId));
                params.put("studyProgress", studyProgress);
                xryUserApplicantService.addCourseStudyProgress(params);
            }
        }
        return Result.ok().put("isLogin", isLogin);
    }

    @SysLog("app保存用户点击分享课程")
    @GetMapping("/courseClickSave")
    @ApiOperation(value = "用户点击分享课程", notes = "用户点击分享课程保存")
    public Result courseClickSave(@RequestParam String uId, Long courseId) {
        CourseClick courseClick = new CourseClick();
        // courseClick.setId(UUIDUtils.getUUID());
        courseClick.setUserId(uId);
        courseClick.setCourseId(courseId);
        List<CourseClick> list = courseClickService.selectClickByUidAndCid(courseClick);
        if (list == null || list.size() < 1) {
            courseClick.setId(UUIDUtils.getUUID());
            courseClick.setCountss(1);
            Boolean aBoolean = courseClickService.courseClickSave(courseClick);
            if (aBoolean) {
                return Result.ok();
            } else {
                return Result.error(203, "保存失败！联系管理员");
            }
        }
        CourseClick courseClick1 = list.get(0);
        courseClick.setId(courseClick1.getId());
        courseClick.setCountss(courseClick1.getCountss() + 1);
        courseClickService.updateClick(courseClick);
        return Result.ok();
    }

    @SysLog("app用户点击课程分享")
    @GetMapping("/courseClickShare")
    @ApiOperation(value = "用户点击课程的分享", notes = "用户点击课程的分享")
    public Result courseClickShare(@RequestParam Long courseId) {
        CourseClick courseClick = new CourseClick();
        courseClick.setUserId(getAppUserId());
        courseClick.setCourseId(courseId);
        List<CourseClick> list = courseClickService.selectClickByUidAndCid(courseClick);
        if (list == null || list.size() < 1) {
            courseClick.setId(UUIDUtils.getUUID());
            courseClick.setCountss(0);
            Boolean aBoolean = courseClickService.courseClickSave(courseClick);
            if (aBoolean) {
                return Result.ok();
            } else {
                return Result.error(203, "保存失败！联系管理员");
            }
        } else {
            return Result.ok();
        }
    }
}
