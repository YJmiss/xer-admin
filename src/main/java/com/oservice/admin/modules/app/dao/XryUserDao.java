package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryUserEntity;

public interface XryUserDao extends BaseMapper<XryUserEntity> {
    /**
     * 通过手机号校验当前手机号是否系统用户
     * @param  param 手机号
     */
    XryUserEntity queryByUserParam(String phone);
}
