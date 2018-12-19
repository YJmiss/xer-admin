package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryUserAttentionDao;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;
import com.oservice.admin.modules.sys.service.XryUserAttentionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户关注表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryUserAttentionService")
public class XryUserAttentionServiceImpl extends ServiceImpl<XryUserAttentionDao, XryUserAttentionEntity> implements XryUserAttentionService {


    @Override
    public Integer appSaveTeacher(Map<String, Object> params) {
        XryUserAttentionEntity userAttention = new XryUserAttentionEntity();
        userAttention.setObjId((String) params.get("objId"));
        userAttention.setUserId((String) params.get("userId"));
        userAttention.setObjType(0);
        userAttention.setObjStatus(0);
        userAttention.setCreateTime(new Date());
        return baseMapper.insert(userAttention);
    }

    @Override
    public PageUtils appPageListTeacherByUserId(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        List<Map<String, Object>> teacherList = baseMapper.appPageListTeacherByUserId(params);
        pageList.setRecords(teacherList);
        return new PageUtils(pageList);
    }

    @Override
    public void appDelTeacherById(Map<String, Object> params) {
        baseMapper.appDelTeacherById(params);
    }

    @Override
    public List<String> listUserIdByTeacherId(String teacherId) {
        List<XryUserAttentionEntity> teacherList = baseMapper.listUserIdByTeacherId(teacherId);
        List<String> userIds = null;
        if (teacherList.size() > 0) {
            userIds = new ArrayList<>();
            for (XryUserAttentionEntity teacher : teacherList) {
                String userId = teacher.getUserId();
                userIds.add(userId);
            }
        }
        return userIds;
    }


}
