package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.app.dao.XryUserDao;
import com.oservice.admin.modules.app.entity.XryUserEntity;
import com.oservice.admin.modules.app.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<XryUserDao, XryUserEntity> implements UserService {

    @Override
    public XryUserEntity queryByUserPhone(String phone) {
        return baseMapper.queryByUserPhone(phone);
    }

    @Override
    public Boolean createToken(XryUserEntity user) {
        return baseMapper.createToken(user);
    }
}
