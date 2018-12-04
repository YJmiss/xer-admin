package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCommentDao;
import com.oservice.admin.modules.sys.dao.XryRecordDao;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.service.XryCommentService;
import com.oservice.admin.modules.sys.service.XryRecordService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 消息表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCommentService")
public class XryCommentServiceImpl extends ServiceImpl<XryCommentDao, XryCommentEntity> implements XryCommentService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        String courseId = (String) params.get("courseId");
        String userId = (String) params.get("userId");
        String type = (String) params.get("type");
        String status  = (String) params.get("status");
        map.put("page",page);
        map.put("limit",limit);
        map.put("courseId",courseId);
        map.put("userId",userId);
        map.put("type",type);
        map.put("status",status);
        List<Map<String, Object>> commentList = baseMapper.pageList(map);
        pageList.setRecords(commentList);
        return new PageUtils(pageList);
    }

    @Override
    public XryCommentEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryCommentEntity xryCommentEntity) {
        baseMapper.insert(xryCommentEntity);
    }

    @Override
    public void update(XryCommentEntity xryCommentEntity) {
        baseMapper.updateById(xryCommentEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void updateCommentStatus(Map<String, Object> params) {
        baseMapper.updateCommentStatus(params);
    }

}
