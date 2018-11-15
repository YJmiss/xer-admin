package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryRoleDao;
import com.oservice.admin.modules.sys.entity.XryRoleEntity;
import com.oservice.admin.modules.sys.service.XryRoleService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 系统用户
 * 角色表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryRoleService")
public class XryRoleServiceImpl extends ServiceImpl<XryRoleDao, XryRoleEntity> implements XryRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XryRoleEntity> page = this.selectPage(new Query<XryRoleEntity>(params).getPage(), new EntityWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public XryRoleEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryRoleEntity xryRoleEntity) {
        baseMapper.insert(xryRoleEntity);
    }

    @Override
    public void update(XryRoleEntity xryRoleEntity) {
        baseMapper.updateById(xryRoleEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        baseMapper.deleteById(ids);
    }
}
