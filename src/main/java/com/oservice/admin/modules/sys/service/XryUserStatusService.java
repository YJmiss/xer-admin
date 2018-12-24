package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import com.oservice.admin.modules.sys.entity.XryUserStatusEntity;

import java.util.Map;

/**
 * 系统用户
 * 用户消息状态表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryUserStatusService extends IService<XryUserStatusEntity> {

    /**
     * 用户读消息，根据消息id
     * @param params
     */
    void updateUserMessageStatusByUserId(Map<String, Object> params);
}
