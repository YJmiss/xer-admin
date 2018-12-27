package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.sys.dao.XryUserCollectDao;
import com.oservice.admin.modules.sys.dao.XryUserFeedbackDao;
import com.oservice.admin.modules.sys.entity.XryUserCollectEntity;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;
import com.oservice.admin.modules.sys.service.XryUserCollectService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户反馈表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryUserFeedbackService")
public class XryUserFeedbackServiceImpl extends ServiceImpl<XryUserFeedbackDao, XryUserFeedbackEntity> implements XryUserFeedbackService {


    @Override
    public void appUserFeedbackByUserId(String userId, String params) {
        JSONObject json = new JSONObject(params);
        String feedbackImg = json.getString("feedbackImg");
        String feedbackInfo = json.getString("feedbackInfo");

        XryUserFeedbackEntity userFeedback = new XryUserFeedbackEntity();
        userFeedback.setFeedbackInfo(feedbackInfo);
        userFeedback.setFeedbackImg(feedbackImg);
        userFeedback.setUserId(userId);
        userFeedback.setObjType(1);
        userFeedback.setObjStatus(0);
        userFeedback.setCheckStatus(0);
        userFeedback.setCreateTime(new Date());
        baseMapper.insert(userFeedback);
    }
}
