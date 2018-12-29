package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCommentQuestionEntity;
import com.oservice.admin.modules.sys.entity.XryUserCollectEntity;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户反馈表接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface XryUserFeedbackService extends IService<XryUserFeedbackEntity> {

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据反馈id，查询反馈
     *
     * @param id
     * @return
     */
    XryUserFeedbackEntity queryById(Long id);

    /** 删除
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * app端用户反馈问题
     *
     * @param userId
     * @param params
     */
    void appUserFeedbackByUserId(String userId, String params);

    /**
     * app查询我的反馈列表
     * @param userId
     */
    List<Map<String, Object>> appListUserFeedbackByUserId(String userId);

    /**
     * 反馈信息
     * @param id
     * @return
     */
    Map<String, Object> queryByIdAndUserId(Long id);
}
