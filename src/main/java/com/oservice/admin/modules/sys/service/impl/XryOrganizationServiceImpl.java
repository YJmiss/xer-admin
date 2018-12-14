package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.dao.XryOrganizationDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryOrganizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 机构表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryOrganizationService")
public class XryOrganizationServiceImpl extends ServiceImpl<XryOrganizationDao, XryOrganizationEntity> implements XryOrganizationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        // 重写分页查询 page limit title cid
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        String orgName = (String) params.get("orgName");
        String corporator = (String) params.get("corporator");
        String status = (String) params.get("status");
        map.put("page", page);
        map.put("limit", limit);
        if (null != orgName && "" != orgName) {
            map.put("orgName","%" + orgName + "%");
        }
        map.put("corporator", corporator);
        map.put("status", status);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public Map<String, Object> queryById(Long id) {
        return baseMapper.queryById(id);
    }

    @Override
    public void save(String[] params) {
        XryOrganizationEntity xryOrganizationEntity = new XryOrganizationEntity();
        xryOrganizationEntity.setUserId(params[1]);
        xryOrganizationEntity.setOrgCode(params[2]);
        xryOrganizationEntity.setOrgName(params[3]);
        xryOrganizationEntity.setCorporator(params[4]);
        xryOrganizationEntity.setIdCard(params[5]);
        xryOrganizationEntity.setContact(params[6]);
        xryOrganizationEntity.setBusinessLicense(params[7]);
        xryOrganizationEntity.setStatus(1);
        xryOrganizationEntity.setCreated(new Date());

        String orgId = params[0];
        if (StringUtils.isNotBlank(orgId)) {
            // 修改保存
            xryOrganizationEntity.setId(Long.valueOf(orgId));
            baseMapper.updateById(xryOrganizationEntity);
        } else {
            // 添加保存
            baseMapper.insert(xryOrganizationEntity);
        }
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
        params.put("id", id);
        params.put("status", action);
        baseMapper.recordExamineInfo(params);
    }

    @Override
    public List<Map<String, Object>> listByUserId(Map<String, Object> params) {
        return baseMapper.listByUserId(params);
    }


}
