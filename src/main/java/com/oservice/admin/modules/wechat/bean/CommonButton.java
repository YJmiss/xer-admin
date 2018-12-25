package com.oservice.admin.modules.wechat.bean;

/**
 * @ClassName: CommonButton
 * @Description: 普通按钮（子按钮）
 * @Author: Administrator
 * @Date: 2018/9/20 12:41
 * @Version 1.0
 */
public class CommonButton extends Button {
    private String type;
    private String key;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
