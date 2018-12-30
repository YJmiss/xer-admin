package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryVideoDao;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
import com.oservice.admin.modules.sys.service.XryVideoService;
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
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String title = (String) params.get("title");
        String courseId = (String) params.get("courseId");
        String catalogId = (String) params.get("catalogId");
        String status = (String) params.get("status");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        if (null != title && "" != title) {
            map.put("title","%"+title+"%");
        }
        map.put("courseId",courseId);
        map.put("catalogId",catalogId);
        if (null != status && "" != status) {
            map.put("status", Integer.valueOf(status));
        }
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
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
    public void recordExamineInfo(XryRecordEntity record) {
        Map<String, Object> params = new HashMap<>();
        // 得到视频id
        Long id = record.getRecordId();
        // 得到操作类型3：通过  4：驳回
        Integer action = record.getActionNumber();
        params.put("id",id);
        params.put("status",action);
        baseMapper.recordExamineInfo(params);
    }

    @Override
    public XryVideoEntity judeVideoIsFullByCourseId(Long catalogId) {
        return baseMapper.judeVideoIsFullByCourseId(catalogId);
    }
}
