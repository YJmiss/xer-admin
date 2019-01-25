package com.oservice.admin.modules.app.service.impl;

import com.oservice.admin.common.utils.JsonUtil;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.modules.app.entity.AppCartAndCollectEntity;
import com.oservice.admin.modules.app.entity.AppCartEntity;
import com.oservice.admin.modules.app.service.CartService;
import com.oservice.admin.modules.app.service.DistributionService;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
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
    @Resource
    private XryTeacherService xryTeacherService;
    @Resource
    private DistributionService distributionService;

    @Override
    public void addCart(XryUserEntity user, long courseId, String sharingId) {
        List<AppCartEntity> appCartEntities = new ArrayList<>();
        String courseJson = redisUtils.get("APPCART" + user.getId());
        XryCourseEntity xryCourse = courseService.queryById(courseId);
        XryTeacherEntity xryTeacher = xryTeacherService.selectById(xryCourse.getTid());
        AppCartEntity cartEntity = new AppCartEntity();
        cartEntity.setId(xryCourse.getId());
        cartEntity.setSharingId(sharingId);
        cartEntity.setImage(xryCourse.getImage());
        cartEntity.setNickname(xryTeacher.getRealName());
        cartEntity.setTitle(xryCourse.getTitle());
        cartEntity.setPrice(xryCourse.getPrice());
        cartEntity.setAppPrice(new BigDecimal(xryCourse.getPrice()).divide(new BigDecimal(100)).setScale(2).toString());
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
    public void deleteCourse(XryUserEntity user, long courseId, String id) {
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
                    if (!cart.getSharingId().equals("sharingId") && !id.equals("0")) {
                        XryUserEntity dUser = new XryUserEntity();
                        dUser.setId(cart.getSharingId());
                        distributionService.createOrder(cart.getId(), dUser, id);
                    }
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
