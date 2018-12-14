package com.oservice.admin.modules.wechat.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.wechat.common.dao.WeixinAuthorizeDao;
import com.oservice.admin.modules.wechat.common.entity.WeixinAuthorize;
import com.oservice.admin.modules.wechat.common.service.WeixinAuthorizeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: oservice
 * @description: 微信授权信息保存
 * @author: YJmiss
 * @create: 2018-12-12 16:01
 **/
@Service
public class WeixinAuthorizeServiceImpl extends ServiceImpl<WeixinAuthorizeDao, WeixinAuthorize> implements WeixinAuthorizeService {

    @Override
    public Boolean addAuthorize(WeixinAuthorize weixinAuthorize) {
        boolean br = baseMapper.addAuthorize(weixinAuthorize);
        return br;
    }

    @Override
    public WeixinAuthorize getAuthorize(int gzhType) {

        return baseMapper.selectAuthorizeBygzhType(1);

    }

    @Override
    public List<WeixinAuthorize> getAuthorizeList() {
        return baseMapper.getAuthorizeList();
    }

    @Override
    public Boolean updataAuthorizeById(WeixinAuthorize weixinAuthorize) {
        // return baseMapper.updataAuthorizeById(weixinAuthorize);
        Integer integer = baseMapper.updateById(weixinAuthorize);
        if (integer == null || integer == 0) {
            return false;
        }
        return true;
    }

    @Override
    public WeixinAuthorize getAuthorizeByid(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Boolean deleteAuthorizeByid(String id) {
        Integer integer = baseMapper.deleteById(id);
        if (integer == null || integer == 0) {
            return false;
        }
        return true;
    }
}
