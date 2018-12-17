package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryRecordDao;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.service.XryRecordService;
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
    /** 课程审核通过常量 */
    final static  Integer VIDEO_EXAMINE_PASS = 3;
    /** 课程审核驳回常量 */
    final static  Integer VIDEO_EXAMINE_REJECT = 4;
    /** 视频审核的标识符 */
    final static  Integer VIDEO_EXAMINE_FLAG = 2;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String examineType = (String) params.get("examineType");
        String examineTitle = (String) params.get("examineTitle");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("title","%" + examineTitle + "%");
        map.put("type",examineType);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
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
    public void recordExamine(XryRecordEntity record, Long userId) {
        record.setUserId(userId);
        record.setCreated(new Date());
        baseMapper.insert(record);
    }

}
