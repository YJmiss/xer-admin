package com.oservice.admin.modules.sys.service.impl;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryUserDao;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
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

}
