package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryMessageDao;
import com.oservice.admin.modules.sys.entity.XryMessageEntity;
import com.oservice.admin.modules.sys.service.XryMessageService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String userId = (String) params.get("userId");
        String objId = (String) params.get("objId");
        String type = (String) params.get("type");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("objId",objId);
        map.put("userId",userId);
        map.put("type",type);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public XryMessageEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Integer save(XryMessageEntity xryMessageEntity) {
        return baseMapper.insert(xryMessageEntity);
    }

    @Override
    public void update(XryMessageEntity xryMessageEntity) {
        baseMapper.updateById(xryMessageEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void updateMessageStatus(Map<String, Object> params) {
        baseMapper.updateMessageStatus(params);
    }

    @Override
    public Map<String, Object> seleMessageById(Long id) {
        return baseMapper.seleMessageById(id);
    }

    @Override
    public Long saveAndGetId(XryMessageEntity message) {
        return baseMapper.saveAndGetId(message);
    }

    @Override
    public Integer countMessageByUserId(Map<String, Object> params) {
        return baseMapper.countMessageByUserId(params);
    }

}
