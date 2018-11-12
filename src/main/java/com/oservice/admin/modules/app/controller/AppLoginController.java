package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.CookieHelper;
import com.oservice.admin.common.utils.MD5Utils;
import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.validator.ValidatorUtils;
import com.oservice.admin.modules.app.entity.XryUserEntity;
import com.oservice.admin.modules.app.form.LoginForm;
import com.oservice.admin.modules.app.service.UserService;
import com.oservice.admin.modules.app.utils.JwtUtils;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static com.oservice.admin.common.utils.SMSUtils.sendTelMessage;

/**
 * @ClassName: AppLoginController
 * @Description: APP登录模块相关登录信息
 * @Author: yiyx
 * @Date: 2018/11/7 08:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/appAccount")
@Api(description = "APP登录api")
public class AppLoginController {

    @Resource
    private UserService userService;


    /**
     * @Description: 发送短信验证码
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @RequestMapping(value = "/sendPhoneCode")
    public Result  sendPhoneCode(String tel) throws ApiException {
        long code = RandomUtils.nextLong(1000, 9000);
        String msg="";
        String template = "{\"code\":\"" + code + "\",\"product\":\"" + msg + "\"}";
        boolean isTrue = sendTelMessage(ConfigConstant.TEMPLATECODE, template, tel);
        String checkCode = MD5Utils.md5(String.valueOf(code).toUpperCase());
        // Todo 存到cook里了
        CookieHelper.addCookie("phoneCodeApp", checkCode);
        // TODO 这里就是取到并校验了

        //  if (MD5.md5(phoneCode.trim().toUpperCase()).equals(CookieHelper.getCookie("phoneCodeApp"))) {
       return Result.ok(String.valueOf(isTrue));
    }

    /**
     * @Description: 通过手机号校验当前手机号是否系统用户
     * @Author: yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @RequestMapping(value = "/checkUserPhone")
    public Result checkUserPhone (String phone) throws Exception{

         if (MD5Utils.md5(phone.trim().toUpperCase()).equals(CookieHelper.getCookie("phoneCodeApp")))
        {
            System.out.print("输入验证码正确");
        }
            XryUserEntity user= new XryUserEntity();
        user =userService.queryByUserParam(phone);
        if(user==null) {
            return Result.error().put("result",false);
        }
        return Result.ok().put("result",true);
    }

}
