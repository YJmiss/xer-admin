package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCommentDao;
import com.oservice.admin.modules.sys.dao.XryMessageDao;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import com.oservice.admin.modules.sys.service.XryCommentService;
import com.oservice.admin.modules.sys.service.XryMessageService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 消息表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryMessageService")
public class XryMessageServiceImpl extends ServiceImpl<XryMessageDao, XryMessageEntity> implements XryMessageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        String objId = (String) params.get("objId");
        String type = (String) params.get("type");
        map.put("page",page);
        map.put("limit",limit);
        map.put("objId",objId);
        map.put("type",type);
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public XryMessageEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryMessageEntity xryMessageEntity) {
        baseMapper.insert(xryMessageEntity);
    }

    @Override
    public void update(XryMessageEntity xryMessageEntity) {
        baseMapper.updateById(xryMessageEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

}
