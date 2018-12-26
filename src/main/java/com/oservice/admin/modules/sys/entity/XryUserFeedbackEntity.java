package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 用户反馈表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_user_feedback")
public class XryUserFeedbackEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     *  对象id（可为空，后台设置“常见问题”，没有用户id）
     */
    private String userId;
    /**
     * 类型（1：用户反馈 2：管理添加的问题）
     */
    private Integer objType;
    /**
     * 状态（0：问题未发布  1：问题发布）
     */
    private Integer objStatus;
    /**
     * 审核状态（0：未审核  1：已回复（针对用户发起的反馈问题，管理回复表示已经审核通过））
     */
    private Integer checkStatus;
    /**
     * 用户反馈信息图片
     */
    private String feedbackImg;
    /**
     * 反馈信息
     */
    private String feedbackInfo;
    /**
     * 管理员回复信息图片
     */
    private String replyImg;
    /**
     * 管理员回复信息
     */
    private String replyInfo;
    /**
     * 创建时间
     */
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Integer checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getFeedbackImg() {
        return feedbackImg;
    }

    public void setFeedbackImg(String feedbackImg) {
        this.feedbackImg = feedbackImg;
    }

    public String getFeedbackInfo() {
        return feedbackInfo;
    }

    public void setFeedbackInfo(String feedbackInfo) {
        this.feedbackInfo = feedbackInfo;
    }

    public String getReplyImg() {
        return replyImg;
    }

    public void setReplyImg(String replyImg) {
        this.replyImg = replyImg;
    }

    public String getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(String replyInfo) {
        this.replyInfo = replyInfo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
