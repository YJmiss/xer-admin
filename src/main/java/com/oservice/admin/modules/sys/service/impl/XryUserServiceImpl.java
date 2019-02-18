package com.oservice.admin.modules.sys.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryUserDao;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * app用户表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryUserService")
public class XryUserServiceImpl extends ServiceImpl<XryUserDao, XryUserEntity> implements XryUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String phone = (String) params.get("phone");
        String status = (String) params.get("status");
        String role = (String) params.get("role");
        String socialSource = (String) params.get("socialSource");
        String recommend = (String) params.get("recommend");
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("phone","%" + phone + "%");
        map.put("status",status);
        map.put("role",role);
        map.put("socialSource",socialSource);
        map.put("recommend",recommend);
        // 查询返回的数据总数page.totalCount
       Long total = baseMapper.countTotal(map);
       pageList.setTotal(total);
       // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }
    
    @Override
    public void deleteBatch(String[] ids) {
        baseMapper.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<XryUserEntity> treeUser() {
        return baseMapper.treeUser();
    }

    @Override
    public void updateUserInfoByUserId(String params, String userId) {
        // 根据用户id查询出用户信息
        XryUserEntity user = baseMapper.selectById(userId);

        JSONObject json = new JSONObject(params);
        String nickname = json.getString("nickname");
        Integer sex = json.getInt("sex");
        String email = json.getString("email");

        user.setEmail(email);
        user.setSex(sex);
        user.setNickname(nickname);
        baseMapper.updateById(user);
    }

    @Override
    public void updateUserHeadImgByUserId(String newHeadImg, String userId) {
        // 根据用户id查询出用户信息
        XryUserEntity user = baseMapper.selectById(userId);
        user.setAvatar(newHeadImg);
        baseMapper.updateById(user);
    }

    @Override
    public void updateUserRole(Map<String, Object> params) {
        baseMapper.updateUserRole(params);
    }

    @Override
    public XryUserEntity queryById(String userId) {
        return baseMapper.queryById(userId);
    }

    @Override
    public String judgeNicknameIsRepet(String params, String userId) {
        JSONObject json = new JSONObject(params);
        String nickname = json.getString("nickname");
        return baseMapper.judgeNicknameIsRepet(nickname, userId);
    }

    @Override
    public String judgeEmailIsRepet(String params, String userId) {
        JSONObject json = new JSONObject(params);
        String email = json.getString("email");
        return baseMapper.judgeEmailIsRepet(email, userId);
    }

}
