package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.AppUserEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface AppUserDao extends BaseMapper<AppUserEntity> {
    /**
     * 通过手机号校验当前手机号是否系统用户
     * @param  phone 手机号
     */
    AppUserEntity queryByUserPhone(String phone);
    /**
     * 保存用户token
     * @param
     */
    Boolean createToken(AppUserEntity user);

    /**
     * 重置密码
     * @param
     */
    Boolean updatePassword(AppUserEntity user);
}
