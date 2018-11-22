package com.oservice.admin.modules.app.controller;

import com.oservice.admin.common.utils.*;
import com.oservice.admin.modules.app.entity.AppUserEntity;
import com.oservice.admin.modules.app.form.LoginForm;
import com.oservice.admin.modules.app.service.UserService;
import com.oservice.admin.modules.sys.controller.AbstractController;
import com.oservice.admin.modules.sys.service.SysUserTokenService;
import com.taobao.api.ApiException;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.oservice.admin.common.utils.SMSUtils.sendTelMessage;

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
public class AppLoginController extends AbstractController {

    @Resource
    private UserService userService;

    @Resource
    private RedisUtils redisUtils;

    @Resource
    private SysUserTokenService sysUserTokenService;

    /**
     * @Description: 发送短信验证码
     * @Author:yiyx
     * @param:
     * @Date: 2018/11/7 9:14
     * @Version 1.0
     */
    @GetMapping(value = "/sendPhoneCode")
    public Result sendPhoneCode(String tel) throws ApiException {
        if (!CheckUtil.isMobile(tel)) {
            return Result.error("手机号格式错误");
        }
        long code = RandomUtils.nextLong(100000, 900000);
        String msg = "";
        String template = "{\"code\":\"" + code + "\",\"product\":\"" + msg + "\"}";
        boolean isTrue = sendTelMessage(ConfigConstant.TEMPLATECODE, template, tel);
        //String checkCode = MD5Utils.md5(String.valueOf(code).toUpperCase());
        // Todo 存到redis里 有效时间：60s
        redisUtils.set("phoneCodeApp" + tel, code, 600);
        //  CookieHelper.addCookie("phoneCodeApp" + tel, checkCode, 60);
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
        System.out.println("验证码：" + param);
        System.out.println("手机号：" + phone);
        System.out.println("yuanz ：" + redisUtils.get("phoneCodeApp" + phone));
        Boolean b = param.equals(redisUtils.get("phoneCodeApp" + phone));
        System.out.println(b);
        if (!CheckUtil.isMobile(phone)) {
            return Result.error("手机号格式错误");
        }
        if (param == null || param.equals("")) {
            return Result.error("验证码为空");
        }
        if (b) {
            return Result.ok().put("result", true);
        } else {
            return Result.ok().put("result", false);
        }
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
        if (!CheckUtil.isMobile(phone)) {
            return Result.error("手机号格式错误");
        }
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
        if (!CheckUtil.isMobile(form.getPhone())) {
            return Result.error("手机号格式错误");
        }
        //用户信息
        AppUserEntity user = userService.queryByUserPhone(form.getPhone());
        /*  測試
        System.out.println(user.getPassword());
        Boolean b = MD5Utils.verify(form.getPassword(),user.getPassword());
        System.out.println(b);
        */
        //账号不存在、密码错误
        if (user == null || !(MD5Utils.verify(form.getPassword(), user.getPassword()))) {
            return Result.error("账号或密码不正确");
        }
        //生成token，并保存到数据库
        Result token = sysUserTokenService.createToken(user.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("user", user);
        return Result.ok(map);
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
        Boolean b = param.equals(redisUtils.get("phoneCodeApp" + phone));
        if (!CheckUtil.isMobile(phone)) {
            return Result.error("手机号格式错误");
        }
        if (param == null) {
            return Result.error("验证码为空");
        }
        AppUserEntity user = userService.queryByUserPhone(phone);
        if (user != null && param.equals(redisUtils.get("phoneCodeApp" + phone))) {
            //生成token，并保存到数据库
            Result token = sysUserTokenService.createToken(user.getId());
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("user", user);
            return Result.ok(map);
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
    public Result setPassword(@RequestBody LoginForm form) {
        System.out.println(form.getPassword() + "------------------" + form.getPhone());
        AppUserEntity user = userService.queryByUserPhone(form.getPhone());
        if (user == null || !CheckUtil.isMobile(form.getPhone())) {
            return Result.error("手机号不正确");
        }
        user.setPassword(MD5Utils.generate(form.getPassword()));
        Boolean br = userService.updatePassword(user);
        if (br) {
            return Result.ok().put("result", true);
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
    @GetMapping("/register")
    public Result register(@RequestParam String param, String phone, String password) {
        if (!CheckUtil.isMobile(phone)) {
            return Result.error("手机号格式错误");
        }
        if (userService.queryByUserPhone(phone) != null) {
            return Result.error("手机号已注册");
        }
        if (!param.equals(redisUtils.get("phoneCodeApp" + phone))) {
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

    /**
     * 退出登陸
     */
    @PostMapping("/logout")
    public Result logout() {
        sysUserTokenService.logout(getAppUserId());
        return Result.ok();
    }
}
