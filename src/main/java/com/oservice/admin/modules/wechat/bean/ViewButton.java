package com.oservice.admin.modules.wechat.bean;

/**
 * @ClassName: ViewButton
 * @Description: view类型的按钮
 * @Author: Administrator
 * @Date: 2018/9/20 12:42
 * @Version 1.0
 */
public class ViewButton extends Button {
    private String type;
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}