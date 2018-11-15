package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryUserDao;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryUserService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * 系统用户
 * app用户表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryUserService")
public class XryUserServiceImpl extends ServiceImpl<XryUserDao, XryUserEntity> implements XryUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XryUserEntity> page = this.selectPage(new Query<XryUserEntity>(params).getPage(), new EntityWrapper<>());

        return new PageUtils(page);
    }

    @Override
    public XryUserEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryUserEntity xryUserEntity) {
        xryUserEntity.setCreated(new Date());
        xryUserEntity.setUpdated(new Date());
        baseMapper.insert(xryUserEntity);
    }

    @Override
    public void update(XryUserEntity xryUserEntity) {
        xryUserEntity.setCreated(new Date());
        xryUserEntity.setUpdated(new Date());
        baseMapper.updateById(xryUserEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        baseMapper.deleteById(ids);
    }
}
