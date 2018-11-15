package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * 系统用户
 * 权限表的实体类
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_permission")
public class XryPermissionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 权限id
     */
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 访问url地址
     */
    private String url;

    /**
     * 是否可用,1：可用，0不可用
     */
    private String available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
