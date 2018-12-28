package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryUserCollectDao;
import com.oservice.admin.modules.sys.dao.XryUserFeedbackDao;
import com.oservice.admin.modules.sys.entity.XryUserCollectEntity;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;
import com.oservice.admin.modules.sys.service.XryUserCollectService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String userInfo = (String) params.get("userInfo");
        String userId = (String) params.get("userId");
        String createTime = (String) params.get("createTime");
        // 审核状态（0：未审核  1：已回复（针对用户发起的反馈问题，管理回复表示已经审核通过））
        String checkStatus = (String) params.get("checkStatus");
        map.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize", pageSize);
        map.put("userId", userId);
        map.put("checkStatus", checkStatus);
        if (null != userInfo && !"".equals(userInfo)) {
            map.put("userInfo", "%" + userInfo + "%");
        }
        if (null != createTime && !"".equals(createTime)) {
            map.put("createTime", "%" + createTime + "%");
        }
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public XryUserFeedbackEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void appUserFeedbackByUserId(String userId, String params) {
        JSONObject json = new JSONObject(params);
        String feedbackImg = json.getString("feedbackImg");
        String feedbackInfo = json.getString("feedbackInfo");

        XryUserFeedbackEntity userFeedback = new XryUserFeedbackEntity();
        userFeedback.setFeedbackInfo(feedbackInfo);
        userFeedback.setFeedbackImg(feedbackImg);
        userFeedback.setUserId(userId);
        userFeedback.setCheckStatus(0);
        userFeedback.setCreateTime(new Date());
        baseMapper.insert(userFeedback);
    }

    @Override
    public List<Map<String, Object>> appListUserFeedbackByUserId(String userId) {
        return baseMapper.appListUserFeedbackByUserId(userId);
    }
}
