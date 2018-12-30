package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 用户报名表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_user_applicant")
public class XryUserApplicantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录id
     */
    private Long id;
    /**
     * 报名对象id
     */
    private Long objId;
    /**
     * 报名用户
     */
    private String userId;
    /**
     * 报名对象类型
     */
    private Integer objType;
    /**
     * 状态
     */
    private Integer objStatus;
    /**
     * 学习进度
     */
    private Integer studyProgress;
    /**
     * 报名时间
     */
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjId() {
        return objId;
    }

    public void setObjId(Long objId) {
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

    public Integer getStudyProgress() { return studyProgress; }

    public void setStudyProgress(Integer studyProgress) { this.studyProgress = studyProgress; }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
