package com.oservice.admin.modules.wechat.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.wechat.common.entity.WeixinAuthorize;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @program: oservice
 * @description: ...
 * @author: YJmiss
 * @create: 2018-12-12 16:06
 **/
@Mapper
public interface WeixinAuthorizeDao extends BaseMapper<WeixinAuthorize> {
    Boolean addAuthorize(WeixinAuthorize weixinAuthorize);

    WeixinAuthorize selectAuthorizeBygzhType(int gzhType);

    List<WeixinAuthorize> getAuthorizeList();
    //Boolean updataAuthorizeById(WeixinAuthorize weixinAuthorize);
}
