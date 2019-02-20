package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.modules.app.entity.XryRecordtimeEntity;
import com.oservice.admin.modules.app.service.RecordtimeService;
import com.oservice.admin.modules.sys.dao.XryCommentDao;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.dao.XryPayOfflineDao;
import com.oservice.admin.modules.sys.entity.*;
import com.oservice.admin.modules.sys.service.*;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * 系统用户
 * 线下支付i表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryPayOfflineService")
public class XryPayOfflineServiceImpl extends ServiceImpl<XryPayOfflineDao, XryPayOfflineEntity> implements XryPayOfflineService {


    @Override
	public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String title = (String) params.get("title");
        String nickname = (String) params.get("nickname");
        String userName = (String) params.get("userName");
        String createTime = (String) params.get("createTime");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        if (null != title && !"".equals(title)) {
            map.put("title","%" + title + "%");
        }
        if (null != nickname && !"".equals(nickname)) {
            map.put("nickname","%" + nickname + "%");
        }
        if (null != userName && !"".equals(userName)) {
            map.put("userName","%" + userName + "%");
        }
        if (null != createTime && !"".equals(createTime)) {
            map.put("createTime", "%" + createTime + "%");
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
	public XryPayOfflineEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryPayOfflineEntity xryPayOfflineEntity) {
        baseMapper.insert(xryPayOfflineEntity);
	}

	@Override
    public void update(XryPayOfflineEntity xryPayOfflineEntity) {
        baseMapper.updateById(xryPayOfflineEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

}
