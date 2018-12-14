package com.oservice.admin.modules.wechat.common.service;

import com.oservice.admin.modules.wechat.common.entity.WeixinAuthorize;

import java.util.List;

public interface WeixinAuthorizeService {
    /**
     * 保存验证信息
     *
     * @param weixinAuthorize 公众号验证信息
     */
    Boolean addAuthorize(WeixinAuthorize weixinAuthorize);

    /**
     * 获取启用用户信息
     *
     * @param gzhType 1 启用标识
     */
    WeixinAuthorize getAuthorize(int gzhType);

    /**
     * 列表所有验证信息
     *
     * @param
     */
    List<WeixinAuthorize> getAuthorizeList();

    /**
     * 更改当前验证信息
     *
     * @param
     */
    Boolean updataAuthorizeById(WeixinAuthorize weixinAuthorize);

    /**
     * 通过ID获取当前验证验证信息
     *
     * @param
     */
    WeixinAuthorize getAuthorizeByid(String id);

    /**
     * 通过ID移除验证信息
     *
     * @param
     */
    Boolean deleteAuthorizeByid(String id);

}
