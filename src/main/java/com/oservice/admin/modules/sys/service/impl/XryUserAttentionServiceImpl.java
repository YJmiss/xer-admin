package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryUserAttentionDao;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;
import com.oservice.admin.modules.sys.service.XryUserAttentionService;
import org.springframework.stereotype.Service;

import java.util.*;

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
        List<String> tokenList = baseMapper.listUserIdByTeacherId(teacherId);
        return tokenList;
    }

    @Override
    public List<XryUserAttentionEntity> countAttentionByTeacherId(String teacherId) {
        return baseMapper.countAttentionByTeacherId(teacherId);
    }

    @Override
    public XryUserAttentionEntity isAttentionByUserIdAndTeacherId(String teacherId, String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId" ,userId);
        params.put("teacherId" ,teacherId);
        return baseMapper.isAttentionByUserIdAndTeacherId(params);
    }


}
