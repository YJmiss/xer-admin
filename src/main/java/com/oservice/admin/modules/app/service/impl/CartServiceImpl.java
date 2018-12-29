package com.oservice.admin.modules.app.service.impl;

import com.oservice.admin.common.utils.JsonUtil;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.modules.app.entity.AppCartAndCollectEntity;
import com.oservice.admin.modules.app.entity.AppCartEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: oservice
 * @description:
 * @author: YJmiss
 * @create: 2018-12-18 13:58
 **/
@Service
public class CartServiceImpl implements CartService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private XryCourseService courseService;

    @Override
    public void addCart(XryUserEntity user, long courseId) {
        List<AppCartEntity> appCartEntities = new ArrayList<>();
        String courseJson = redisUtils.get("APPCART" + user.getId());
        XryCourseEntity xryCourse = courseService.queryById(courseId);
        AppCartEntity cartEntity = new AppCartEntity();
        cartEntity.setId(xryCourse.getId());
        cartEntity.setImage(xryCourse.getImage());
        cartEntity.setNickname(user.getNickname());
        cartEntity.setTitle(xryCourse.getTitle());
        cartEntity.setPrice(xryCourse.getPrice());
        if (StringUtils.isNotBlank(courseJson)) {
            appCartEntities = JsonUtil.jsonToList(courseJson, AppCartEntity.class);
        }
        appCartEntities.add(cartEntity);
        redisUtils.set("APPCART" + user.getId(), appCartEntities);
    }

    @Override
    public List<AppCartEntity> getCartListFromRedis(XryUserEntity user) {
        String courseJson = redisUtils.get("APPCART" + user.getId());
        if (StringUtils.isNotBlank(courseJson)) {
            return JsonUtil.jsonToList(courseJson, AppCartEntity.class);
        }
        return null;
    }

    @Override
    public List<AppCartAndCollectEntity> getCartListIsCollectFromRedis(XryUserEntity user) {
        String courseJson = redisUtils.get("APPCART" + user.getId());
        if (StringUtils.isNotBlank(courseJson)) {
            return JsonUtil.jsonToList(courseJson, AppCartAndCollectEntity.class);
        }
        return null;
    }

    @Override
    public void deleteCourse(XryUserEntity user, long courseId) {
        List<AppCartEntity> appCartEntities = JsonUtil.jsonToList(redisUtils.get("APPCART" + user.getId()), AppCartEntity.class);
        List<AppCartEntity> appCartEntities1 = new ArrayList<>();
        if (appCartEntities.size() > 0) {
            for (AppCartEntity cartEntity : appCartEntities) {
                if (courseId == cartEntity.getId()) {
                    appCartEntities1.add(cartEntity);
                }
            }
            if (appCartEntities1.size() > 0) {
                for (AppCartEntity cart : appCartEntities1) {
                    appCartEntities.remove(cart);
                }
            }
            redisUtils.set("APPCART" + user.getId(), appCartEntities);
        }
    }

    @Override
    public void removeCart(XryUserEntity user) {
        if (redisUtils.hasKey("APPCART" + user.getId())) {
            redisUtils.delete("APPCART" + user.getId());
        }
    }
}
