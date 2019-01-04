package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 标记用户删除消息的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_delete_message")
public class XryDeleteMessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 用户id
     */
    private String userId;
    /**
     * 消息id
     */
    private Long msgId;
    /**
     * 用户消息状态（0：未删除  1：删除）
     */
    private Integer msgStatus;

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

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public Integer getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(Integer msgStatus) {
        this.msgStatus = msgStatus;
    }
}
