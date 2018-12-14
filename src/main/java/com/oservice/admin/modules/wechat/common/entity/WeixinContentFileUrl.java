package com.oservice.admin.modules.wechat.common.entity;

import java.io.Serializable;

public class WeixinContentFileUrl implements Serializable {
    private String locContentFile;

    private String wxContentFile;

    private static final long serialVersionUID = 1L;

    public String getLocContentFile() {
        return locContentFile;
    }

    public void setLocContentFile(String locContentFile) {
        this.locContentFile = locContentFile == null ? null : locContentFile.trim();
    }

    public String getWxContentFile() {
        return wxContentFile;
    }

    public void setWxContentFile(String wxContentFile) {
        this.wxContentFile = wxContentFile == null ? null : wxContentFile.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", locContentFile=").append(locContentFile);
        sb.append(", wxContentFile=").append(wxContentFile);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}