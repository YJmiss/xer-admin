package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryUserEntity;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface XryUserDao extends BaseMapper<XryUserEntity> {
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
}
