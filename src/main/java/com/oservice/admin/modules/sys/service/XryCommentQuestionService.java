package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCommentQuestionEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 常见问题表接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface XryCommentQuestionService extends IService<XryCommentQuestionEntity> {

    /**
     * 分页查询
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);
    
    /**
     * 根据问题id，查询问题
     * @param id
     * @return
     */
    XryCommentQuestionEntity queryById(Long id);

    /**
     * 问题保存
     * @param xryCommentQuestionEntity
     */
    void save(XryCommentQuestionEntity xryCommentQuestionEntity);

    /**
     * 问题修改
     * @param xryCommentQuestionEntity
     */
    void update(XryCommentQuestionEntity xryCommentQuestionEntity);

    /**
     * 问题删除
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 修改常见问题的状态
     * @param params
     */
    void updateQuestionStatus(Map<String, Object> params);

    /**
     * app查询常见问题列表
     * @param flag
     * @return
     */
    List<Map<String, Object>> appListCommentQuestionByUserId(Integer flag);
}
