package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 用户评论表的实体类
 *
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_comment")
public class XryCommentEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 评论id
     */
    private Long id;
    /**
     * 被评价对（课程、讲师）象id
     */
    private String objId;
    /**
     * 评价用户id
     */
    private String userId;

    /**
     * 星级评分：2、4、6、8、10'
     */
    private Integer starLevel;

    /**
     * 类型：0：课程评价；1：讲师评价
     */
    private Integer type;

    /**
     * 状态：1：正常；0：删除
     */
    private Integer status;

    /**
     * 评价内容
     */
    private String detail;

    /**
     * 评价内容
     */
    private String reply;

    /**
     * 回复时间
     */
    private Date replyTime;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 修改时间
     */
    private Date updateTime;


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

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
