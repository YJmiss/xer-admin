package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.app.entity.AppCartEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.oss.cloud.OSSFactory;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryRecommendEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.util.*;

/**
 * 系统用户
 * app端个人中心的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appUserCenter")
@Api(description = "app端个人中心的控制器")
public class AppUserCenterController extends AbstractController {
     @Resource
     private XryCourseService xryCourseService;
     @Resource
     private XryCourseCatService xryCourseCatService;
     @Resource
     private ShiroService shiroService;
     @Resource
     private XryTeacherService xryTeacherService;
     @Resource
     private XryUserService xryUserService;
     @Resource
     private XryUserApplicantService xryUserApplicantService;
     @Resource
     private XryMessageService xryMessageService;
    @Resource
    private CartService cartService;

    /**
     * 用户进入修改资料页面查询用户信息
     * @param request
     * @return
     */
    @GetMapping("/userCenter/queryUserInfoByUserId")
    @ApiOperation(value = "用户进入修改资料页面查询用户信息（昵称、性别、邮箱、头像）", notes = "需要在请求头里加token参数")
    public Result queryUserInfoByUserId(HttpServletRequest request) {
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
        XryUserEntity userInfo = xryUserService.selectById(userId);
        return Result.ok().put("userInfo", userInfo);
    }

    /**
     * 根据userId查询用户已经选择喜好的课程类目
     * 不需要根据类目分类显示
     * @return
     */
    @GetMapping("/userCenter/listCourseCatByUserId")
    @ApiOperation(value = "查询用户已经选择喜好的课程类目，即“我的兴趣”", notes = "需要在请求头里加token参数")
    public Result listCourseCatByUserId(HttpServletRequest request) {
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
        params.put("userId",userId);
        XryRecommendEntity recommend = xryCourseService.listRecommendCourseCatByUserId(params);
        String[] courseCatIds = recommend.getCatId().split(",");
        List<XryCourseCatEntity> courseCatList = new ArrayList<>();
        if (courseCatIds.length > 0) {
            for (String courseCatId : courseCatIds) {
                XryCourseCatEntity courseCat = xryCourseCatService.selectById(courseCatId);
                courseCatList.add(courseCat);
            }
        }
        return Result.ok().put("courseCatList",courseCatList);
    }

    /**
     * 查询用户已经关注的讲师数
     * @param request
     * @return
     */
    @GetMapping("/userCenter/countUserApplicantByUserId")
    @ApiOperation(value = "查询用户已经关注的讲师数，购物车商品数量、消息数、头像", notes = "需要在请求头里加token参数")
    public Result countUserApplicantByUserId(HttpServletRequest request) {
        Map<String, Object> params = new HashMap<>();
        XryUserEntity userInfo = new XryUserEntity();
        Integer courseMessageCount = 0;
        Integer teacherMessageCount = 0;
        Integer userApplicantCount = 0;
        Integer userCartCount = 0;
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            }
            XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
            String userId = users.getId();
            // 查询用户课程未读消息数（课程消息、我关注的）
            params.put("userId", userId);
            courseMessageCount = xryMessageService.countCourseMessageByUserId(params);
            teacherMessageCount = xryMessageService.countTeacherMessageByUserId(params);
            // 关注的讲师数
            userApplicantCount = xryTeacherService.countUserApplicantByUserId(userId);
            // 购物车数量
            List<AppCartEntity> cartList = cartService.getCartListFromRedis(xryUserService.selectById(userId));
            if (cartList == null) {
                userCartCount = 0;
            } else if (cartList.size() > 0) {
                userCartCount = cartList.size();
            } else {
                userCartCount = 0;
            }
            // 查询用户头像
//            userInfo = xryUserService.selectById(userId);
            userInfo = xryUserService.queryById(userId);
        }
        Map<String, Object> map = new HashMap<>();
        // 1、查询用户已经关注的讲师数
        map.put("userApplicantCount", userApplicantCount);
        // 2、查询用户购物车商品数量
        map.put("userCartCount", userCartCount);
        // 3、查询用户消息数
        Integer systemMessageCount = xryMessageService.countSystemMessage();
        Integer userMessageCount = courseMessageCount + teacherMessageCount + systemMessageCount;
        map.put("userMessageCount", userMessageCount);
        // 4、查询用户头像
        map.put("userInfo", userInfo);
        return Result.ok().put("params", map);
    }

    /**
     * 个人中心修改个人资料接口
     * @param params
     * @param request
     * @return
     */
    @GetMapping("/userCenter/updateUserInfoByUserId")
    @ApiOperation(value = "个人中心修改个人资料接口", notes = "params：携带昵称、性别、邮箱信息的参数；需要在请求头里加token参数")
    public Result updateUserInfoByUserId(@RequestParam String params, HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            } else {
                XryUserEntity users = shiroService.queryUsers(tokenEntity.getUserId());
                String userId = users.getId();
                // 判断该昵称已被其他用户使用
                String nickname = xryUserService.judgeNicknameIsRepet(params, userId);
                if (StringUtils.isNotBlank(nickname)) {
                    return Result.error(205, "该昵称已被其他用户使用，请重新填写");
                }
                // 判断该邮箱已被其他用户绑定
                String email = xryUserService.judgeEmailIsRepet(params, userId);
                if (StringUtils.isNotBlank(email)) {
                    return Result.error(206, "该邮箱已被其他用户绑定，请重新填写");
                }
                xryUserService.updateUserInfoByUserId(params, userId);
            }
        }
        return Result.ok();
    }

    /**
     * 个人中心头像上传
     * @param newHeadImg
     * @param request
     * @return
     */
    @GetMapping("/userCenter/updateUserHeadImgByUserId")
    @ApiOperation(value = "个人中心头像上传接口", notes = "newHeadImg：是头像的URL；需要在请求头里加token参数")
    public Result updateUserHeadImgByUserId(@RequestParam String newHeadImg, HttpServletRequest request) {
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
        xryUserService.updateUserHeadImgByUserId(newHeadImg, userId);
        return Result.ok();
    }

    /**
     * 清除最近学习课程
     * 不删除数据库数据
     * @param 
     * @param request
     * @return
     */
    @GetMapping("/userCenter/removeUserCourseByUserId")
    @ApiOperation(value = "清除最近学习课程，不删除数据库数据", notes = "需要在请求头里加token参数")
    public Result removeUserCourseByUserId(HttpServletRequest request) {
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
        xryUserApplicantService.removeUserCourseByUserId(userId);
        return Result.ok();
    }

    /**
     * 查询最近浏览课程列表
     * @param ids
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GetMapping("/userCenter/listCourseByUserIdAndCourseId")
    @ApiOperation(value = "查询最近浏览课程列表", notes = "ids：储存在localStorage里的课程id，数组；")
    public Result listCourseByUserIdAndCourseId(@RequestParam String[] ids, @RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Map<String, Object> params = new HashMap<>();
        List<Map<String, Object>> courseList = new ArrayList<>();
        for (int i=0;i<ids.length;i++) {
            if (0 == i) {
                String firstItemId = ids[0].split("\\[")[1];
                params.put("id", firstItemId);
            } else if (ids.length-1 == i) {
                String lastItemId = ids[ids.length-1].split("\\]")[0];
                params.put("id", lastItemId);
            } else {
                params.put("id", ids[i]);
            }
            params.put("pageSize", pageSize);
            params.put("pageNo", (pageNo - 1) * pageSize);
            Map<String, Object> course = xryCourseService.listCourseByUserIdAndCourseId(params);
            if (null != course) {
                courseList.add(course);
            }
        }
        return Result.ok().put("courseList", courseList);
    }

    /**
     * app端图片上传到图片服务器
     *
     * @return
     * @throws Exception
     */
    @PostMapping("/appUploadImg")
    @ApiOperation(value = "app端图片上传到图片服务器", notes = "需要在请求头里加token参数")
    public Result appUploadImg(MultipartHttpServletRequest multipartRequest) {
        Map map = new HashMap<>();
        try {
            MultipartFile uploadFile = multipartRequest.getFile("file");
            if (uploadFile == null) {
                Collection<Part> parts = multipartRequest.getParts();
                for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext(); ) {
                    Part part = iterator.next();
                    //2.根据上传文件分析出基本后缀名
                    String fileName = OSSFactory.generateFileName(part.getSubmittedFileName(), part.getContentType());
                    //设置文件的保存路径
                    //3.得到文件的内容（二进制数据）
                    // 把InputStream转成byte[]
                    byte[] fileByte = OSSFactory.inputStreamToByteArr(part.getInputStream());
                    String url = OSSFactory.build().uploadSuffix(fileByte, fileName);
                    map.put("url", url);
                }
                return Result.ok(map);
            }
            return Result.error(500, "上传失败文件服务器配置错误，联系管理员");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", 1);
            map.put("message", "图片上传失败！");
        }
        return Result.ok(map);
    }

}
