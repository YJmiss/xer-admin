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
     * 构造讲师树
     * @return
     */
    List<XryUserEntity> treeUser();


    /**
     * 讲师推荐、取消推荐
     * @param params
     */
    void updateUserRecommend(Map<String, Object> params);

}
