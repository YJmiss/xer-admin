package com.oservice.admin.modules.app.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.modules.app.entity.AppUserEntity;

public interface UserService extends IService<AppUserEntity> {
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

    /**
     * 注册用户
     *
     * @param
     */
    void register(AppUserEntity user);

    /**
     * 保存用户签名
     *
     * @paramAZ
     */
    void insertSignByid(String userId, String sign);

    /**
     * 新增刷新令牌时间
     *
     * @param
     */
    void insertReTokByid(String userId, String reToken);

    /**
     * 查看刷新令牌时间
     *
     * @param
     */
    String queryReTokenBySign(String sign);

    /**
     * 修改刷新令牌时间
     *
     * @param
     */
    void updataReTokByid(String userId, String reToken);

    /**
     * 删除令牌相关
     *
     * @param
     */
    void deleteTokenBySign(String sign);
}
