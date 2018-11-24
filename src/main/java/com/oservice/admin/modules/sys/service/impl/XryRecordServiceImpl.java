package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryRecordDao;
import com.oservice.admin.modules.sys.dao.XryVideoDao;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.service.XryRecordService;
import com.oservice.admin.modules.sys.service.XryVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 审核记录表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryRecordService")
public class XryRecordServiceImpl extends ServiceImpl<XryRecordDao, XryRecordEntity> implements XryRecordService {
    /** 课程审核通过常量 */
    final static  Integer COURSE_EXAMINE_PASS = 3;
    /** 课程审核驳回常量 */
    final static  Integer COURSE_EXAMINE_REJECT = 4;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        String phone = (String) params.get("phone");
        String status = (String) params.get("status");
        String role = (String) params.get("role");
        String socialSource = (String) params.get("socialSource");

        map.put("page",page);
        map.put("limit",limit);
        map.put("phone","%"+phone+"%");
        map.put("status",status);
        map.put("role",role);
        map.put("socialSource",socialSource);
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public XryRecordEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryRecordEntity xryRecordEntity) {
        baseMapper.insert(xryRecordEntity);
    }

    @Override
    public void update(XryRecordEntity xryRecordEntity) {
        baseMapper.updateById(xryRecordEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void recordCourseExamine(Map<String, Object> params) {
        Long[] ids = (Long[]) params.get("ids");
        Long userId = (Long) params.get("userId");
        Integer flag = (Integer) params.get("flag");
        Integer type = (Integer) params.get("type");
        for (Long id : ids) {
            XryRecordEntity xryRecordEntity = new XryRecordEntity();
            xryRecordEntity.setRecordId(id);
            xryRecordEntity.setUserId(userId);
            xryRecordEntity.setType(type);
            xryRecordEntity.setActionNumber(flag);
            xryRecordEntity.setCreated(new Date());
            baseMapper.insert(xryRecordEntity);
        }
    }

}
