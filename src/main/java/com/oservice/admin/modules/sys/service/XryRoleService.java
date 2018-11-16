package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryRoleEntity;

import java.util.Map;

/**
 * 系统用户
 * 角色表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryRoleService extends IService<XryRoleEntity> {

    /**
     * 角色分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据角色id，查询角色
     *
     * @param id
     * @return
     */
    XryRoleEntity queryById(Long id);

    /**
     * app角色保存
     *
     * @param xryRoleEntity
     */
    void save(XryRoleEntity xryRoleEntity);

    /**
     * 角色修改
     *
     * @param xryRoleEntity
     */
    void update(XryRoleEntity xryRoleEntity);

    /**
     * 角色删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

}
