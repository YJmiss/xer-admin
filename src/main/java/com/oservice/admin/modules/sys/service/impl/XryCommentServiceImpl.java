package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.sys.dao.XryCommentDao;
import com.oservice.admin.modules.sys.dao.XryRecordDao;
import com.oservice.admin.modules.sys.entity.XryCommentEntity;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.service.XryCommentService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import com.oservice.admin.modules.sys.service.XryRecordService;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;

/**
 * 系统用户
 * 消息表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCommentService")
public class XryCommentServiceImpl extends ServiceImpl<XryCommentDao, XryCommentEntity> implements XryCommentService {
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private SolrJDao solrJDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String ,Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String courseId = (String) params.get("courseId");
        String userId = (String) params.get("userId");
        String type = (String) params.get("type");
        String status  = (String) params.get("status");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("courseId",courseId);
        map.put("userId",userId);
        map.put("type",type);
        map.put("status",status);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
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

    @Override
    public void insertCommentByUserId(String params, String userId)  {
        JSONObject json = new JSONObject(params);
        // 取出app传过来的参数
        String objId = json.getString("objId");
        Integer type = json.getInt("type");
        Integer starLevel = json.getInt("starLevel");
        String detail = json.getString("detail");

        XryCommentEntity comment = new XryCommentEntity();
        comment.setObjId(objId);
        comment.setType(type);
        comment.setStatus(1);
        comment.setUserId(userId);
        comment.setStarLevel(starLevel);
        comment.setDetail(detail);
        comment.setCreated(new Date());
        baseMapper.insert(comment);

        if (0 == type) {
            //获取评论百分数
            Long courseId = Long.valueOf(objId);
            Integer feedback = xryCourseService.getFeedback(courseId);
            try {
                solrJDao.update(courseId,feedback,0);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SolrServerException e) {
                e.printStackTrace();
            }
        }
    }

}
