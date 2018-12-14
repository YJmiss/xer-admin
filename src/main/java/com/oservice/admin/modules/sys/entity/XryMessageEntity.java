package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 消息表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_message")
public class XryMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 消息id
     */
    private Long id;

    /**
     * 消息类型（1：课程消息；2：我关注的；3：平台通知）
     */
    private Integer msgType;

    /**
     * 课程消息类型（1：开课通知；2：课程章节更新，3：其它）
     */
    private Integer courseType;

    /**
     * 被发送消息对象id
     */
    private Long objId;

    /**
     * 讲师id
     */
    private String userId;

    /**
     * 发布状态（0：未发布；1：已发布）
     */
    private Integer status;

    /**
     * 发布日期
     */
    private Date publishDate;

    /**
     * 具体消息
     */
    private String info;

    /**
     * 创建时间
     */
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMsgType() {
        return msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
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

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
