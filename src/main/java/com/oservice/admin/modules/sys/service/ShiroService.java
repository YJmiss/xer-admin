package com.oservice.admin.modules.sys.service;

import com.oservice.admin.modules.sys.entity.SysUserEntity;
import com.oservice.admin.modules.sys.entity.SysUserTokenEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;

import java.util.Set;

/**
 * shiro相关接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询系统用户
     * @param userId
     */
    SysUserEntity queryUser(String userId);

    /**
     * 根据用户ID，查询APP用户
     *
     * @param id
     */
    XryUserEntity queryUsers(String id);
}
