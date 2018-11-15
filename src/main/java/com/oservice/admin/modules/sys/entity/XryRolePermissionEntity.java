package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 系统用户
 * 角色权限表的实体类
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_role_permission")
public class XryRolePermissionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色权限id
     */
    private String id;

    /**
     * 角色id
     */
    private String xryRoleId;

    /**
     * 权限id
     */
    private String xryPermissionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXryRoleId() {
        return xryRoleId;
    }

    public void setXryRoleId(String xryRoleId) {
        this.xryRoleId = xryRoleId;
    }

    public String getXryPermissionId() {
        return xryPermissionId;
    }

    public void setXryPermissionId(String xryPermissionId) {
        this.xryPermissionId = xryPermissionId;
    }
}
