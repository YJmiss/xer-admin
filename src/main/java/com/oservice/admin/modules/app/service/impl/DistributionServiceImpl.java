package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.modules.app.dao.DistributionOrderDao;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.app.entity.DistributionOrder;
import com.oservice.admin.modules.app.information.TallyOrderService;
import com.oservice.admin.modules.app.service.DistributionService;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 分销订单服务
 * @author: YJmiss
 * @create: 2019-01-10 13:33
 **/
@Service("distributionService")
public class DistributionServiceImpl extends ServiceImpl<DistributionOrderDao, DistributionOrder> implements DistributionService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private XryCourseService xryCourseService;
    @Resource
    private TallyOrderService tallyOrderService;
    @Autowired
    private SolrJDao solrJDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> map = new HashMap<>();
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String distributionId = (String) params.get("distributionId");
        String time = (String) params.get("createTime");
        String status = (String) params.get("status");
        if (null != time && !"".equals(time)) {
            String[] yearArr = time.split(" 年 ");
            String year = yearArr[0];
            String[] monthArr = yearArr[1].split(" 月 ");
            String month = monthArr[0];
            String day = monthArr[1].split(" 日")[0];
            time = year + "-" + month + "-" + day;
            map.put("createdTime", "%" + time + "%");
        }
        map.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize", pageSize);
        map.put("id", distributionId);
        map.put("status", status);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal1(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList1(map);
        if (courseList.size() > 0) {
            for (Map<String, Object> courses : courseList) {
                Long brokerage = (Long) courses.get("brokerage");
                String s = new BigDecimal(brokerage).divide(new BigDecimal(100)).setScale(2).toString();
                courses.put("appBrokerage", s);
            }
        }
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    /**
     * 分销订单生成
     * @param id:课程ID user:用户 orderId:订单ID
     */
    @Override
    public void createOrder(Long id, XryUserEntity user, String orderId) {
        DistributionOrder distribution = new DistributionOrder();
        distribution.setId(redisUtils.getId());
        distribution.setCourseId(id);
        distribution.setUserId(user.getId());
        distribution.setOrderId(orderId);
        distribution.setCreateTime(new Date());
        distribution.setUpdateTime(new Date());
        distribution.setStatus(0);
        distribution.setGrade(1);
        XryCourseEntity xryCourseEntity = xryCourseService.queryById(id);
        long price = xryCourseEntity.getPrice();
        Long brokerage = 0l;
        brokerage = tallyOrderService.getBrokerage(price);
        distribution.setBrokerage(brokerage);
        baseMapper.insertDistribution(distribution);
    }

    @Override
    public List<Map<String, Object>> courseList() {
        try {
            return solrJDao.findall();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
