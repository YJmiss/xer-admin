package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryVideoDao;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
import com.oservice.admin.modules.sys.service.XryVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 视频表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryVideoService")
public class XryVideoServiceImpl extends ServiceImpl<XryVideoDao, XryVideoEntity> implements XryVideoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        // 所属课程搜索
        String courseId = (String) params.get("courseId");
        String catalogId = (String) params.get("catalogId");
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String page = (String) params.get("page");
        String limit = (String) params.get("limit");
        map.put("page",page);
        map.put("title","%"+title+"%");
        map.put("limit",limit);
        map.put("courseId",courseId);
        map.put("catalogId",catalogId);
        List<Map<String, Object>> courseList = baseMapper.pageList(map);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public XryVideoEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryVideoEntity xryVideoEntity) {
        baseMapper.insert(xryVideoEntity);
    }

    @Override
    public void update(XryVideoEntity xryVideoEntity) {
        baseMapper.updateById(xryVideoEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void updateVideoStatus(Map<String, Object> params) {
        baseMapper.updateVideoStatus(params);
    }
}
