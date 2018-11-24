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
     * 根据app用户id，查询app用户
     *
     * @param id
     * @return
     */
    XryUserEntity queryById(Long id);

    /**
     * app用户保存
     *
     * @param xryUserEntity
     */
    void save(XryUserEntity xryUserEntity);

    /**
     * app用户修改
     *
     * @param xryUserEntity
     */
    void update(XryUserEntity xryUserEntity);

    /**
     * app用户删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 构造讲师树
     * @return
     */
    List<XryUserEntity> treeUser();
}
