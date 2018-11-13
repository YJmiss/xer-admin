package com.oservice.admin.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 * @author LingDu
 * @version 1.0
 */
@ApiModel(value = "登录表单")
public class LoginForm {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message="手机号不能为空")
    private String phone;

    @ApiModelProperty(value = "密码")
    @NotBlank(message="密码不能为空")
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
