package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryUserDao;
import com.oservice.admin.modules.sys.entity.SysUserEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.apache.commons.lang.StringUtils;
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
        // 不用自定义查询
        String phone = (String) params.get("phone");
        String status = (String) params.get("status");
        String role = (String) params.get("role");
        String socialSource = (String) params.get("socialSource");
        Page<XryUserEntity> page = this.selectPage(new Query<XryUserEntity>(params).getPage(),
            new EntityWrapper<XryUserEntity>()
                .like(StringUtils.isNotBlank(phone),"phone", phone)
                    .like(StringUtils.isNotBlank(status),"status", status)
                    .like(StringUtils.isNotBlank(role),"role", role)
                    .like(StringUtils.isNotBlank(socialSource),"social_source", socialSource)
        );

        return new PageUtils(page);
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
    public void updateUserRole(Map<String, Object> params) {
        baseMapper.updateUserRole(params);
    }

}
