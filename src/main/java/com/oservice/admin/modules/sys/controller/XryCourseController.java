package com.oservice.admin.modules.sys.controller;

import com.oservice.admin.common.annotation.SysLog;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.common.validator.group.AddGroup;
import com.oservice.admin.common.validator.group.UpdateGroup;
import com.oservice.admin.modules.app.service.SolrJService;
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
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程表的控制器
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/xry/course")
public class XryCourseController extends AbstractController {
    /** 课程上架标识符常量 */
    final static Integer ADD_TO_COURSE = 4;
    /** 课程下架标识符常量 */
    final static Integer DEL_FROM_COURSE = 5;
    /** 课程推荐 */
    final static Integer RECOMMEND_COURSE = 1;
    /** 取消课程推荐 */
    final static Integer CANCEL_RECOMMEND = 0;
    /** 课程加入学习的标识符 */
    final static Integer COURSE_JOIN_STUDY = 1;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private SysUserTokenService sysUserTokenService;
    @Resource
    private XryCourseTeacherUserService xryCourseTeacherUserService;
    @Autowired
    private SolrJService solrJService;

    
    /**
     * 查询课程列表
     * @param params
     * @return
     */
    @SysLog("查询课程列表")
    @GetMapping("/list")
    @RequiresPermissions("xry:course:list")
    public Result list(@RequestParam Map<String, Object> params){
        PageUtils page = xryCourseService.queryPage(params);
        return Result.ok().put("page", page);
    }

    /**
     * 查询课程审核列表
     * @param params
     * @return
     */
    @SysLog("查询课程审核列表")
    @GetMapping("/examineList")
    @RequiresPermissions("xry:course:examineList")
    public Result examineList(@RequestParam Map<String, Object> params){
        // 只查询未审核和未通过的课程
        PageUtils page = xryCourseService.examineList(params);
        return Result.ok().put("page", page);
    }

    /**
     * 保存课程
     * @param course
     * @return
     */
    @SysLog("保存课程")
    @PostMapping("/save")
    @RequiresPermissions("xry:course:save")
    public Result save(@RequestBody XryCourseEntity course){
        ValidatorUtils.validateEntity(course, AddGroup.class);
        xryCourseService.save(course);
        return Result.ok();
    }

    /**
     * 课程信息
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("xry:course:info")
    public Result info(@PathVariable("id") Long id){
        XryCourseEntity course = xryCourseService.queryById(id);
        return Result.ok().put("course", course);
    }

    /**
     * 修改课程
     * @param course
     * @return
     */
    @SysLog("修改课程")
    @PostMapping("/update")
    @RequiresPermissions("xry:course:update")
    public Result update(@RequestBody XryCourseEntity course){
        ValidatorUtils.validateEntity(course, UpdateGroup.class);
        xryCourseService.update(course);
        return Result.ok();
    }

    /**
     * 删除课程
     * @param ids
     * @return
     */
    @SysLog("删除课程")
    @PostMapping("/delete")
    @RequiresPermissions("xry:course:delete")
    public Result delete(@RequestBody Long[] ids){
        // 删除课程，同事删除课程对应的课程描述和课程目录
        for (Long id : ids) {
            XryCourseCatalogEntity xryCourseCatalogEntity = xryCourseService.queryCourseCatalogByCourseId(id);
            if (null != xryCourseCatalogEntity) {
                return Result.error("请先删除该课程下的课程目录");
            }
            XryCourseDescEntity xryCourseDescEntity = xryCourseService.queryCourseDescById(id);
            if (null != xryCourseDescEntity) {
                return Result.error("请先删除该课程下的课程描述");
            }
            solrJService.deleteIndexById(id);
        }
        xryCourseService.deleteBatch(ids);
        return Result.ok();
    }

    /**
     * 构造课程树
     *
     * @return
     */
    @SysLog("课程树")
    @GetMapping("/treeCourse")
    @RequiresPermissions("xry:course:treeCourse")
    public Result treeCourse() {
        List<XryCourseEntity> courseList = xryCourseService.treeCourse();
        return Result.ok().put("courseList", courseList);
    }

    /**
     * 课程上架：4
     * @param ids
     * @return
     */
    @SysLog("课程上架")
    @PostMapping("/addToCourse")
    @RequiresPermissions("xry:course:addToCourse")
    public Result addToCourse(@RequestBody Long[] ids) {
        boolean br = false;
        // 课程上架之前先判断课程是否已经审核 审核状态：1、2、4
        for (Long id:ids) {
            XryCourseEntity xryCourseEntity = xryCourseService.queryById(id);
            if (1 == xryCourseEntity.getStatus() || 2 == xryCourseEntity.getStatus() || 4 == xryCourseEntity.getStatus()) {
                return Result.error("所选记录中有未审核的课程，请先审核通过该课程再进行此操作");
            }
            br = solrJService.addIndexById(id);
        }
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",ADD_TO_COURSE);
        xryCourseService.updateCourseStatus(params);
        if (br) {
            return Result.ok();
        } else {
            return Result.ok("同步索引失败、请联系管理员！");
        }
    }

    /**
     * 课程下架：5
     * @param ids
     * @return
     */
    @SysLog("课程下架")
    @PostMapping("/delFromCourse")
    @RequiresPermissions("xry:course:delFromCourse")
    public Result delFromCourse(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",DEL_FROM_COURSE);
        xryCourseService.updateCourseStatus(params);
        for (Long id : ids) {
            solrJService.deleteIndexById(id);
        }
        return Result.ok();
    }

    /**
     * 一件导入索引库
     *
     * @return
     */
    @SysLog("导入索引库")
    @GetMapping("/importindex")
    @RequiresPermissions("xry:course:importindex")
    public Result importAllData() throws Exception {
        return solrJService.addIndex();
    }

    /**
     * 推荐课程
     * @param ids
     * @return
     */
    @SysLog("推荐课程")
    @PostMapping("/recommendCourse")
    @RequiresPermissions("xry:course:recommendCourse")
    public Result recommendCourse(@RequestBody Long[] ids) {
        // 推荐课程时，如果课程的状态是未审核、或者未通过，不能推荐
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        params.put("flag",RECOMMEND_COURSE);
        xryCourseService.updateCourseRecommend(params);
        return Result.ok();
    }

    /**
     * 取消推荐课程
     * @param ids
     * @return
     */
    @SysLog("取消推荐课程")
    @PostMapping("/cancelRecommend")
    @RequiresPermissions("xry:course:cancelRecommend")
    public Result cancelRecommend(@RequestBody Long[] ids) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("ids",ids);
        params.put("flag",CANCEL_RECOMMEND);
        xryCourseService.updateCourseRecommend(params);
        return Result.ok();
    }

    /**
     * app端用户加入课程学习
     * @param token 用户的token
     * @param courseId 课程id
     * @param isSelect 是否加入学习
     * @return
     */
    @SysLog("app端用户加入课程学习")
    @PostMapping("/appJoinCourseStudy")
    public Result appJoinCourseStudy(@RequestParam String token, Long courseId, boolean isSelect) {
        // 从token中获取登录人信息
        JSONObject tokenJSONObject = new JSONObject(token);
        String json = tokenJSONObject.getString("token");
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(json);
        if (null == tokenEntity) {
            return Result.error(1,"token已过期，请重新登录！");
        }
        // 把课程id和用户id加入到数据库表中
        Map<String,Object> params = new HashMap<>();
        params.put("userId",tokenEntity.getUserId());
        params.put("courseId",courseId);
        params.put("type",COURSE_JOIN_STUDY);
        Integer isSuccess = xryCourseTeacherUserService.appSaveCourse(params);
        if (1 == isSuccess) {
            return Result.ok().put("1","课程加入学习成功");
        } else {
            return Result.error(2,"课程加入学习失败");
        }
    }

    /**
     * app端根据用户查询用户加入学习的课程列表
     * @param token
     * @param pageNo
     * @param pageSize
     * @param flag 标识符（本周学习、本月学习，全部课程）
     * @return
     */
    @SysLog("app端根据用户查询用户加入学习的课程列表")
    @GetMapping("/appPageListCourseByUserId")
    public Result appPageListCourseByUserId(@RequestParam String token, Integer pageNo, Integer pageSize, Integer flag){
        // 从token中获取登录人信息
        JSONObject tokenJSONObject = new JSONObject(token);
        String json = tokenJSONObject.getString("token");
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(json);
        if (null == tokenEntity) {
            return Result.error(1,"token已过期，请重新登录！");
        }
        // 把课程id和用户id加入到数据库表中
        Map<String,Object> params = new HashMap<>();
        params.put("userId",tokenEntity.getUserId());
        params.put("pageNo",pageNo);
        params.put("pageSize",pageSize);
        params.put("flag",flag);
        PageUtils page = xryCourseTeacherUserService.appPageListCourseByUserId(params);
        return Result.ok().put("page", page);
    }

    /**
     * app端用户删除已经加入学习的课程
     * @param ids
     * @return
     */
    @SysLog("app端用户删除已经加入学习的课程")
    @PostMapping("/appDelCourseById")
    public Result appDelCourseById(@RequestParam String token, Long[] ids){
        // 从token中获取登录人信息
        JSONObject tokenJSONObject = new JSONObject(token);
        String json = tokenJSONObject.getString("token");
        SysUserTokenEntity tokenEntity = sysUserTokenService.selectByToken(json);
        if (null == tokenEntity) {
            return Result.error(1,"token已过期，请重新登录！");
        }
        Map<String,Object> params = new HashMap<>();
        params.put("ids",ids);
        xryCourseTeacherUserService.appDelCourseById(params);
        return Result.ok();
    }
    

}
