package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.app.dao.XryUserDao;
import com.oservice.admin.modules.app.entity.XryUserEntity;
import com.oservice.admin.modules.app.service.UserService;

public class UserServiceImpl extends ServiceImpl<XryUserDao, XryUserEntity> implements UserService {

    @Override
    public XryUserEntity queryByUserParam(String phone) {
        return baseMapper.queryByUserParam(phone);
    }
}
