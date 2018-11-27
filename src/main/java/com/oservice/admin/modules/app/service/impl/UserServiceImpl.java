package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.app.dao.AppUserDao;
import com.oservice.admin.modules.app.entity.AppUserEntity;
import com.oservice.admin.modules.app.service.UserService;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<AppUserDao, AppUserEntity> implements UserService {

    @Override
    public AppUserEntity queryByUserPhone(String phone) {
        return baseMapper.queryByUserPhone(phone);
    }

    @Override
    public Boolean createToken(AppUserEntity user) {
        return baseMapper.createToken(user);
    }

    @Override
    public Boolean updatePassword(AppUserEntity user) {
        return baseMapper.updatePassword(user);
    }

    @Override
    public void register(AppUserEntity user) {
        baseMapper.register(user);
    }

    @Override
    public void insertSignByid(String userId, String sign) {

    }

    @Override
    public void insertReTokByid(String userId, String reToken) {

    }

    @Override
    public String queryReTokenBySign(String sign) {
        return null;
    }

    @Override
    public void updataReTokByid(String userId, String reToken) {

    }

    @Override
    public void deleteTokenBySign(String sign) {

    }
}
