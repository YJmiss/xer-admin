package com.oservice.admin.modules.app.service;

import com.oservice.admin.modules.app.entity.XryUserEntity;
import com.baomidou.mybatisplus.service.IService;

public interface UserService extends IService<XryUserEntity>{
    /**
     * 通过手机号校验当前手机号是否系统用户
     * @param  phone 手机号
     */
    XryUserEntity queryByUserPhone(String phone);

    /**
     * 保存用户token
     * @param
     */
    Boolean createToken(XryUserEntity user);
    /**
     * 重置密码
     * @param
     */
    Boolean updatePassword(XryUserEntity user);

}
