package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryTeacherDao;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 讲师表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryTeacherService")
public class XryTeacherServiceImpl extends ServiceImpl<XryTeacherDao, XryTeacherEntity> implements XryTeacherService {

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        String realName = (String) params.get("realName");
        String status = (String) params.get("status");
        map.put("page",page);
        map.put("limit",limit);
        map.put("realName","%"+realName+"%");
        map.put("status",status);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
		return new PageUtils(pageList);
	}

    @Override
	public XryTeacherEntity queryById(Long id) {
        queryById(id);

		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryTeacherEntity xryTeacherEntity) {
        xryTeacherEntity.setCreated(new Date());
        baseMapper.insert(xryTeacherEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }


    @Override
    public void recordExamineInfo(XryRecordEntity record) {
        Map<String, Object> params = new HashMap<>();
        Long id = record.getRecordId();
        Integer action = record.getActionNumber();
        params.put("id",id);
        params.put("status",action);
        baseMapper.recordExamineInfo(params);
    }


}
