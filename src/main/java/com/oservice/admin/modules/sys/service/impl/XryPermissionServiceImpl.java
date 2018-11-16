package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryPermissionDao;
import com.oservice.admin.modules.sys.entity.XryPermissionEntity;
import com.oservice.admin.modules.sys.service.XryPermissionService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 系统用户
 * 角色表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryPermissionService")
public class XryPermissionServiceImpl extends ServiceImpl<XryPermissionDao, XryPermissionEntity> implements XryPermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XryPermissionEntity> page = this.selectPage(new Query<XryPermissionEntity>(params).getPage(), new EntityWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public XryPermissionEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryPermissionEntity xryPermissionEntity) {
        baseMapper.insert(xryPermissionEntity);
    }

    @Override
    public void update(XryPermissionEntity xryPermissionEntity) {
        baseMapper.updateById(xryPermissionEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        baseMapper.deleteById(ids);
    }
}
