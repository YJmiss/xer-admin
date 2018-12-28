package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryCommentQuestionDao;
import com.oservice.admin.modules.sys.dao.XryUserFeedbackDao;
import com.oservice.admin.modules.sys.entity.XryCommentQuestionEntity;
import com.oservice.admin.modules.sys.entity.XryUserFeedbackEntity;
import com.oservice.admin.modules.sys.service.XryCommentQuestionService;
import com.oservice.admin.modules.sys.service.XryUserFeedbackService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 常见问题表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCommentQuestionService")
public class XryCommentQuestionServiceImpl extends ServiceImpl<XryCommentQuestionDao, XryCommentQuestionEntity> implements XryCommentQuestionService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String title = (String) params.get("title");
        String createTime = (String) params.get("createTime");
        // 状态（0：问题未发布  1：问题发布）
        String questionStatus = (String) params.get("questionStatus");
        map.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize", pageSize);
        if (null != title && !"".equals(title)) {
            map.put("title", "%" + title + "%");
        }
        map.put("questionStatus", questionStatus);
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
    public XryCommentQuestionEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryCommentQuestionEntity commentQuestion) {
        commentQuestion.setQuestionStatus(0);
        commentQuestion.setCreateTime(new Date());
        baseMapper.insert(commentQuestion);
    }

    @Override
    public void update(XryCommentQuestionEntity xryCommentQuestionEntity) {
        baseMapper.updateById(xryCommentQuestionEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public void updateQuestionStatus(Map<String, Object> params) {
        baseMapper.updateQuestionStatus(params);
    }

    @Override
    public List<Map<String, Object>> appListCommentQuestionByUserId(Integer flag) {
        return baseMapper.appListCommentQuestionByUserId(flag);
    }

}
