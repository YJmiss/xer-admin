package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 系统用户
 * 用户角色表的实体类
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_user_role")
public class XryUserRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户视频id
     */
    private String id;

    /**
     * 用户ID
     */
    private String xryUserId;

    /**
     * 视频ID
     */
    private String xryRoleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXryUserId() {
        return xryUserId;
    }

    public void setXryUserId(String xryUserId) {
        this.xryUserId = xryUserId;
    }

    public String getXryRoleId() {
        return xryRoleId;
    }

    public void setXryRoleId(String xryRoleId) {
        this.xryRoleId = xryRoleId;
    }
}
