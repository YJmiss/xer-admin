package com.oservice.admin.modules.wechat.common.entity;

import java.io.Serializable;
import java.util.Date;

public class WeixinMaterial implements Serializable {
    private String mtId;

    private String thumbMediaId;

    private String mediaId;

    private String fileMediaId;

    private String title;

    private String author;

    private String mark;

    private String digest;

    private Integer showCoverPic;

    private String content;

    private String contentSourceUrl;

    private String creatorId;

    private Date createTime;

    private String locationFileUrl;

    private String wxFileUrl;

    private Integer invalid;

    private Integer mtType;

    private Integer isSingle;

    private Integer sort;

    private static final long serialVersionUID = 1L;

    public String getMtId() {
        return mtId;
    }

    public void setMtId(String mtId) {
        this.mtId = mtId == null ? null : mtId.trim();
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId == null ? null : thumbMediaId.trim();
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId == null ? null : mediaId.trim();
    }

    public String getFileMediaId() {
        return fileMediaId;
    }

    public void setFileMediaId(String fileMediaId) {
        this.fileMediaId = fileMediaId == null ? null : fileMediaId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark == null ? null : mark.trim();
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest == null ? null : digest.trim();
    }

    public Integer getShowCoverPic() {
        return showCoverPic;
    }

    public void setShowCoverPic(Integer showCoverPic) {
        this.showCoverPic = showCoverPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentSourceUrl() {
        return contentSourceUrl;
    }

    public void setContentSourceUrl(String contentSourceUrl) {
        this.contentSourceUrl = contentSourceUrl == null ? null : contentSourceUrl.trim();
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId == null ? null : creatorId.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getLocationFileUrl() {
        return locationFileUrl;
    }

    public void setLocationFileUrl(String locationFileUrl) {
        this.locationFileUrl = locationFileUrl == null ? null : locationFileUrl.trim();
    }

    public String getWxFileUrl() {
        return wxFileUrl;
    }

    public void setWxFileUrl(String wxFileUrl) {
        this.wxFileUrl = wxFileUrl == null ? null : wxFileUrl.trim();
    }

    public Integer getInvalid() {
        return invalid;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getMtType() {
        return mtType;
    }

    public void setMtType(Integer mtType) {
        this.mtType = mtType;
    }

    public Integer getIsSingle() {
        return isSingle;
    }

    public void setIsSingle(Integer isSingle) {
        this.isSingle = isSingle;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mtId=").append(mtId);
        sb.append(", thumbMediaId=").append(thumbMediaId);
        sb.append(", mediaId=").append(mediaId);
        sb.append(", fileMediaId=").append(fileMediaId);
        sb.append(", title=").append(title);
        sb.append(", author=").append(author);
        sb.append(", mark=").append(mark);
        sb.append(", digest=").append(digest);
        sb.append(", showCoverPic=").append(showCoverPic);
        sb.append(", content=").append(content);
        sb.append(", contentSourceUrl=").append(contentSourceUrl);
        sb.append(", creatorId=").append(creatorId);
        sb.append(", createTime=").append(createTime);
        sb.append(", locationFileUrl=").append(locationFileUrl);
        sb.append(", wxFileUrl=").append(wxFileUrl);
        sb.append(", invalid=").append(invalid);
        sb.append(", mtType=").append(mtType);
        sb.append(", isSingle=").append(isSingle);
        sb.append(", sort=").append(sort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}