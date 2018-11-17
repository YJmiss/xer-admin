package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.*;
import com.oservice.admin.modules.app.entity.AppUserEntity;
import com.oservice.admin.modules.app.form.LoginForm;
import com.oservice.admin.modules.app.service.UserService;
import com.oservice.admin.modules.app.utils.JwtUtils;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

import static com.oservice.admin.common.utils.SMSUtils.sendTelMessage;

//import io.jsonwebtoken.Claims;
//import java.util.Date;

/**
 * @ClassName: AppLoginController
 * @Description: APP登录模块相关登录信息
 * @Author:yiyx
 * @Date: 2018/11/7 08:45
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/appAccount")
@Api(description = "APP登录/注册api")
public class AppLoginController {

    @Resource
    private UserService userService;

    @Resource
    private JwtUtils jwtUtils;

    /**
     * @Description: 发送短信验证码
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @GetMapping(value = "/sendPhoneCode")
    public Result sendPhoneCode(String tel) throws ApiException {
        long code = RandomUtils.nextLong(100000, 900000);
        String msg = "";
        String template = "{\"code\":\"" + code + "\",\"product\":\"" + msg + "\"}";
        boolean isTrue = sendTelMessage(ConfigConstant.TEMPLATECODE, template, tel);
        String checkCode = MD5Utils.md5(String.valueOf(code).toUpperCase());
        // Todo 存到cook里 有效时间：60s
        CookieHelper.addCookie("phoneCodeApp" + tel, checkCode, 60);
        return Result.ok(String.valueOf(isTrue));
    }

    /**
     * @Description: 校验手机验证码是否正确
     * @Author:yiyx
     * @param:
     * @Date: 2018/1/12 9:36
     * @Version 1.0
     */
    @GetMapping(value = "/validationPhoneCode")
    public Result validationPhoneCode
    (@RequestParam String param, String phone) {
        if (param != null && phone != null) {
            if (MD5Utils.md5(param.trim().toUpperCase()).equals(CookieHelper.getCookie("phoneCodeApp" + phone))) {
                return Result.ok().put("result", true);
            }
        }
        return Result.ok().put("result", false);
    }

    /**
     * @Description: 通过手机号校验当前手机号是否系统用户
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @GetMapping(value = "/checkUserPhone")
    public Result checkUserPhone(String phone) {
        AppUserEntity user = new AppUserEntity();
        user = userService.queryByUserPhone(phone);
        if (user == null) {
            return Result.error().put("result", false);
        }
        return Result.ok().put("result", true);
    }

    /**
     * @Description: 手机号+密码登录
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @PostMapping(value = "/password/login")
    public Result loginForPassword(@RequestBody LoginForm form) {
        //用户信息
        AppUserEntity user = userService.queryByUserPhone(form.getPhone());
/*        System.out.println(user.getPassword());
        Boolean b = MD5Utils.verify(form.getPassword(),user.getPassword());
        System.out.println(b);*/
        ////账号不存在、密码错误
        if (user == null || !(MD5Utils.verify(form.getPassword(), user.getPassword()))) {
            return Result.error("账号或密码不正确");
        }
      /*  //生成token，并保存到数据库
        String token = jwtUtils.generateToken(user.getId());
        //生成更新token(MD5+时间)
        String refreshToken=MD5Utils.md5(DateUtils.format(new Date()));
        System.err.print(DateUtils.format(new Date()));
        user.setLoginToken(token);
        user.setRefreshToken(refreshToken);
        userService.createToken(user);
        Claims claims=jwtUtils.getClaimByToken(token);
        System.err.print(claims.getSubject());
        */
        return Result.ok().put("result", "认证通过");
    }

    /**
     * @Description: 手机号+短信登陆
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @GetMapping(value = "/sms/login")
    public Result loginForSMS(@RequestParam String param, String phone) {
        if (param != null && phone != null) {
            if (MD5Utils.md5(param.trim().toUpperCase()).equals(CookieHelper.getCookie("phoneCodeApp" + phone))) {
                return Result.ok().put("result", "认证通过");
            }
            return Result.error("手机号或验证码不正确");
        }
        return Result.error("手机号或验证码不能为空");
    }

    /**
     * @Description: 重置密码
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @PostMapping(value = "/setPassword")
    public Result setPassword(@RequestParam String param, String phone) {
        AppUserEntity user = userService.queryByUserPhone(phone);
        if (user == null) {
            return Result.error("手机号不正确");
        }
        user.setPassword(MD5Utils.generate(param));
        Boolean br = userService.updatePassword(user);
        if (br) {
            return Result.ok().put("result", "密码重置成功");
        }
        return Result.error("密码重置失败");
    }

    /**
     * @Description: 用户注册
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @PostMapping("/register")
    public Result register(@RequestParam String param, String phone, String password) {
        if (userService.queryByUserPhone(phone) != null) {
            return Result.error("手机号已注册");
        }
        if (!(MD5Utils.md5(param.trim().toUpperCase()).equals(CookieHelper.getCookie("phoneCodeApp" + phone)))) {
            return Result.error("验证码错误");
        }
        AppUserEntity user = new AppUserEntity();
        user.setPassword(MD5Utils.generate(password));
        user.setPhone(phone);
        user.setId(UUIDUtils.getUUID());
        user.setCreated(DateUtils.stringToDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
        user.setUpdated(DateUtils.stringToDate(DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
        user.setSocialSource(0);
        userService.register(user);
        return Result.ok().put("result", "注册成功");
    }


}
