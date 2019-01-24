package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryCommentQuestionEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.ShiroService;
import com.oservice.admin.modules.sys.service.XryCommentQuestionService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
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
 * 系统用户
 * app端用户反馈问题的控制器
 *
 * @author wujunquan
 * @version 1.0
 */
@RestController
@RequestMapping("/api/appUserFeedback")
@Api(description = "app端用户反馈问题的控制器")
public class AppUserFeedbackController extends AbstractController {
    @Resource
    private ShiroService shiroService;
    @Resource
    private XryUserFeedbackService xryUserFeedbackService;
    @Resource
    private XryCommentQuestionService xryCommentQuestionService;

    /**
     * app端用户反馈问题
     * @param params
     * @return
     */
    @GetMapping("/appUserFeedbackByUserId")
    @ApiOperation(value="app端用户反馈问题接口",notes="params：携带用户反馈图片（可空）、反馈具体信息，必填；需要在请求头里携带token")
    public Result appUserFeedbackByUserId(@RequestParam String params, HttpServletRequest request) {
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
        xryUserFeedbackService.appUserFeedbackByUserId(userId, params);
        return Result.ok();
    }

    /**
     * app查询我的反馈列表
     * @param request
     * @return
     */
    @PostMapping("/appListUserFeedbackByUserId")
    @ApiOperation(value="app查询我的反馈列表",notes="需要在请求头里携带token")
    public Result appListUserFeedbackByUserId(HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        Map<String, Object> params = new HashMap<>();
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(204, "token失效，请重新登录");
            } else {
                // 查询用户头像，用户名
                XryUserEntity user = shiroService.queryUsers(tokenEntity.getUserId());
                params.put("user", user);
                // 列表查询
                List<Map<String, Object>> userFeedbackList = xryUserFeedbackService.appListUserFeedbackByUserId(user.getId());
                if (null != userFeedbackList) {
                    List<String> feedbackImgArray = new ArrayList<>();
                    for (Map<String, Object> map : userFeedbackList) {
                        String feedbackImg = (String) map.get("feedback_img");
                        if (StringUtils.isNotBlank(feedbackImg) && !"[]".equals(feedbackImg)) {
                            String img = feedbackImg.split("\\[")[1];
                            img = img.split("\\]")[0];
                            if (StringUtils.isNotBlank(img)) {
                                String[] imgArr = img.split(",");
                                for (String img2 : imgArr) {
                                    img2 = img2.trim();
                                    feedbackImgArray.add(img2);
                                }
                                map.put("feedbackImgArray", feedbackImgArray);
                            }
                        }
                    }
                }
                params.put("userFeedbackList", userFeedbackList);
                // 我的反馈列表数量
                Integer userFeedbackCount = 0;
                if (null != userFeedbackList) userFeedbackCount = userFeedbackList.size();
                params.put("userFeedbackCount",  userFeedbackCount);
            }
        }
        return Result.ok().put("params" ,params);
    }

    /**
     * app查询常见问题列表
     * @param request
     * @param flag
     * @return
     */
    @PostMapping("/appListCommentQuestionByUserId")
    @ApiOperation(value="app查询常见问题列表",notes="params：0表示查询6条；1查询所有；需要在请求头里携带token")
    public Result appListCommentQuestionByUserId(@RequestParam Integer flag, HttpServletRequest request) {
        String accessToken = request.getHeader("token");
        if (StringUtils.isNotBlank(accessToken)) {
            SysUserTokenEntity tokenEntity = shiroService.queryByToken(accessToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                return Result.error(401, "token失效，请重新登录");
            }
        }
        // 进入问题反馈界面，查询6条常见问题，以添加时间降序排序
        // flag:0表示查询6条；1查询所有
        List<Map<String, Object>> commentQuestionList = xryCommentQuestionService.appListCommentQuestionByUserId(flag);
        return Result.ok().put("commentQuestionList" ,commentQuestionList);
    }

    /**
     * app查询问题详情
     *
     * @param
     * @param
     * @return
     */
    @PostMapping("/appListCommentQuestionByQuestionId")
    @ApiOperation(value = "app查询问题详情", notes = "Qid：问题id")
    public Result appListCommentQuestionByQuestionId(@RequestParam Integer Qid) {
        XryCommentQuestionEntity xryCommentQuestionEntity = xryCommentQuestionService.selectById(Qid);
        return Result.ok().put("Question", xryCommentQuestionEntity);
    }
}
