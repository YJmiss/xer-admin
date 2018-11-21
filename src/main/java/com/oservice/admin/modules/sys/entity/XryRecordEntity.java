package com.oservice.admin.modules.sys.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户
 * 审核记录表的实体类
 * @author wujunquan
 * @version 1.0
 */
@TableName("xry_record")
public class XryRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 记录id
     */
    private Long id;
    /**
     * 被审核对象（课程、视频）id
     */
    private Long recordId;
    /**
     * 审核人id
     */
    private Long userId;

    /**
     * 审核记录的标识符
     */
    private Integer type;

    /**
    * 事件动作
    */
   private Integer actionNumber;

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

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getActionNumber() {
        return actionNumber;
    }

    public void setActionNumber(Integer actionNumber) {
        this.actionNumber = actionNumber;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
