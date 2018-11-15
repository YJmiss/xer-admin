package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 系统用户
 * 角色表的实体类
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_role")
public class XryRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 角色id
     */
    private String id;

    /**
     * 角色名字
     */
    private String name;

    /**
     * 是否可用,1：可用，0不可用
     */
    private String available;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
