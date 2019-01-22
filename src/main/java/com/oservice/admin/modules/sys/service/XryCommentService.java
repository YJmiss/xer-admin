package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;

import java.util.Map;

/**
 * 系统用户
 * 评论表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryCommentService extends IService<XryCommentEntity> {

    /**
     * 评论分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据评论id，查询评论
     *
     * @param id
     * @return
     */
    XryCommentEntity queryById(Long id);

    /**
     * 记录保存
     *
     * @param xryCommentEntity
     */
    void save(XryCommentEntity xryCommentEntity);

    /**
     * 评论修改
     *
     * @param xryCommentEntity
     */
    void update(XryCommentEntity xryCommentEntity);

    /**
     * 评论删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 不显示评论、恢复评论显示
     * @param params
     */
    void updateCommentStatus(Map<String, Object> params);

    /**
     * app端用户评论保存
     * @param params
     */
    void insertCommentByUserId(Map<String, Object> params);

    /**
     * 根据用户id和对象id查询评论
     * @param params
     * @return
     */
    Map<String, Object> selectCommentByUserIdAndObjId(Map<String, Object> params);

    /**
     * 评论修改
     * @param params
     */
    void updateByCommentId(Map<String, Object> params);
}
