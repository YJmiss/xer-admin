package com.oservice.admin.modules.wechat.bean;

/**
 * @ClassName: ComplexButton
 * @Description: 复杂按钮（父按钮）
 * @Author: Administrator
 * @Date: 2018/9/20 12:42
 * @Version 1.0
 */
public class ComplexButton extends Button {
    private Button[] sub_button;

    public Button[] getSub_button() {
        return sub_button;
    }

    public void setSub_button(Button[] sub_button) {
        this.sub_button = sub_button;
    }
}
