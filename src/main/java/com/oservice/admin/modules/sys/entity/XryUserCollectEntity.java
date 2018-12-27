package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 用户收藏表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_user_collect")
public class XryUserCollectEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录id
     */
    private Long id;
    /**
     * 收藏对象id
     */
    private String objId;
    /**
     * 收藏用户id
     */
    private String userId;
    /**
     * 类型（1：课程收藏）
     */
    private Integer objType;
    /**
     * 状态（1：收藏  0：取消收藏）
     */
    private Integer objStatus;
    /**
     * 收藏时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjId() {
        return objId;
    }

    public void setObjId(String objId) {
        this.objId = objId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getObjType() {
        return objType;
    }

    public void setObjType(Integer objType) {
        this.objType = objType;
    }

    public Integer getObjStatus() {
        return objStatus;
    }

    public void setObjStatus(Integer objStatus) {
        this.objStatus = objStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
