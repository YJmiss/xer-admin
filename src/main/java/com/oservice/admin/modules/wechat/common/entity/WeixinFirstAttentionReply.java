package com.oservice.admin.modules.wechat.common.entity;

import java.io.Serializable;

public class WeixinFirstAttentionReply implements Serializable {
    private String id;

    private Integer replyType;

    private String replyContentId;

    private Integer openKeyword;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getReplyType() {
        return replyType;
    }

    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    public String getReplyContentId() {
        return replyContentId;
    }

    public void setReplyContentId(String replyContentId) {
        this.replyContentId = replyContentId == null ? null : replyContentId.trim();
    }

    public Integer getOpenKeyword() {
        return openKeyword;
    }

    public void setOpenKeyword(Integer openKeyword) {
        this.openKeyword = openKeyword;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", replyType=").append(replyType);
        sb.append(", replyContentId=").append(replyContentId);
        sb.append(", openKeyword=").append(openKeyword);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}