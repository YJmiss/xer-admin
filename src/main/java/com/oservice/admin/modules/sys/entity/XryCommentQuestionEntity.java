package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 常见问题表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_comment_question")
public class XryCommentQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 状态（0：问题未发布  1：问题发布）
     */
    private Integer questionStatus;
    /**
     * 常见问题图片
     */
    private String questionImg;
    /**
     * 常见问题信息
     */
    private String questionInfo;
    /**
     * 管理员回复图片
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getQuestionStatus() {
        return questionStatus;
    }

    public void setQuestionStatus(Integer questionStatus) {
        this.questionStatus = questionStatus;
    }

    public String getQuestionImg() {
        return questionImg;
    }

    public void setQuestionImg(String questionImg) {
        this.questionImg = questionImg;
    }

    public String getQuestionInfo() {
        return questionInfo;
    }

    public void setQuestionInfo(String questionInfo) {
        this.questionInfo = questionInfo;
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
