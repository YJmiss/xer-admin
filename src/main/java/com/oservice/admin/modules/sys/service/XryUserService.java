package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * app用户表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryUserService extends IService<XryUserEntity> {

    /**
     * app用户分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * app用户删除
     *
     * @param ids
     */
    void deleteBatch(String[] ids);

    /**
     * 构造用户树
     * @return
     */
    List<XryUserEntity> treeUser();

    /**
     * 个人中心修改个人资料接口
     * @param params
     * @param userId
     */
    void updateUserInfoByUserId(String params, String userId);

    /**
     *
     * @param newHeadImg
     * @param userId
     */
    void updateUserHeadImgByUserId(String newHeadImg, String userId);

    /**
     * 讲师切换<->普通用户
     * @param params
     */
    void updateUserRole(Map<String, Object> params);

    /**
     * 
     * @param userId
     * @return
     */
    XryUserEntity queryById(String userId);
}
