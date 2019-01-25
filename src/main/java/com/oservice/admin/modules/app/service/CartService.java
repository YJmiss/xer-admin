package com.oservice.admin.modules.app.service;

import com.oservice.admin.modules.app.entity.AppCartAndCollectEntity;
import com.oservice.admin.modules.app.entity.AppCartEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;

import java.util.List;

/**
 * @program: oservice
 * @description: 购物车服务
 * @author: YJmiss
 * @create: 2018-12-18 13:57
 **/
public interface CartService {
    void addCart(XryUserEntity user, long courseId, String sharingId);

    List<AppCartEntity> getCartListFromRedis(XryUserEntity user);

    List<AppCartAndCollectEntity> getCartListIsCollectFromRedis(XryUserEntity user);

    void deleteCourse(XryUserEntity user, long courseId, String id);

    void removeCart(XryUserEntity user);
}
