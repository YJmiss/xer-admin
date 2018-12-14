package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.app.service.SolrJService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.SysUserTokenService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryCourseTeacherUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app端课程的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appCourse")
public class AppCourseController extends AbstractController {
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private XryCourseTeacherUserService xryCourseTeacherUserService;
    @Autowired
    private SolrJService solrJService;

    /**
     * app端课程详情查询
     * @param token
     * @param courseId
     * @return
     */
    @SysLog("app端课程详情查询")
    @GetMapping("/appQueryCourseDetailByCourseId")
    public Result appQueryCourseDetailByCourseId(@RequestParam String token, @RequestParam Long courseId){
        if (null == courseId) {
            return Result.error(1,"查询出错");
        }
        Map<String, Object> courseDetail = new HashMap<>();
        // 查询"课程详情"
        Map<String, Object> courseDetailContent = xryCourseService.queryCourseDetailContentByCourseId(courseId);
        courseDetail.put("courseDetailContent",courseDetailContent);
        // 查询课程"目录"
        courseDetail.put("courseCatalogList","");
        // 查询课程"评价"
        courseDetail.put("courseCommentList","");
        // 查询"相关课程"
        courseDetail.put("relatedCourseList","");
        return Result.ok().put("courseDetail",courseDetail);
    }
    

}
