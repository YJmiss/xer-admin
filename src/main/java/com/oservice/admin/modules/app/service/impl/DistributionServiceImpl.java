package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.ConfigConstant;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.RedisUtils;
import com.oservice.admin.modules.app.dao.DistributionOrderDao;
import com.oservice.admin.modules.app.dao.SolrJDao;
import com.oservice.admin.modules.app.entity.DistributionOrder;
import com.oservice.admin.modules.app.information.DistributionConfig;
import com.oservice.admin.modules.app.information.TallyOrderService;
import com.oservice.admin.modules.app.service.DistributionService;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.entity.XryUserEntity;
import com.oservice.admin.modules.sys.service.SysConfigService;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

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
    private final static String KEY = ConfigConstant.DISTRIBUTIONCONFIG_CONFIG_KEY;
    @Resource
    private SysConfigService sysConfigService;

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

    @Override
    public Map<String, Object> myEarnings(String appUserId) {
        Map<String, Object> map = new HashMap<>();
        DistributionConfig config = sysConfigService.getConfigObject(KEY, DistributionConfig.class);
        int cashWithdrawal = Integer.parseInt(config.getCashWithdrawal());
        Long myEarnings = baseMapper.myEarnings(appUserId);
        long okEarnings = 0l;
        int a = cashWithdrawal * 100;
        if (myEarnings > a || myEarnings == a) {
            okEarnings = myEarnings;
        }
        map.put("myEarnings", new BigDecimal(myEarnings).divide(new BigDecimal(100)).setScale(2).toString());
        map.put("okEarnings", new BigDecimal(okEarnings).divide(new BigDecimal(100)).setScale(2).toString());
        map.put("cashWithdrawal", cashWithdrawal);
        return map;
    }

    @Override
    public List<Map<String, Object>> accountBalance(String appUserId) {
        List<DistributionOrder> list = baseMapper.accountBalance(appUserId);
        List<Map<String, Object>> list1 = new ArrayList<>();
        if (list == null || list.size() < 1) {
            return null;
        }
        for (DistributionOrder distributionOrder : list) {
            int status = distributionOrder.getStatus();
            if (status == 2) {
                Map<String, Object> map = new HashMap<>();
                map.put("status", "未提现");
                map.put("brokerage", new BigDecimal(distributionOrder.getBrokerage()).divide(new BigDecimal(100)).setScale(2).toString());
                map.put("update_time", distributionOrder.getUpdateTime());
                Long price = xryCourseService.queryById(distributionOrder.getCourseId()).getPrice();
                map.put("price", new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString());
                list1.add(map);
            }
            if (status == 3) {
                Map<String, Object> map = new HashMap<>();
                map.put("status", "已提现");
                map.put("brokerage", new BigDecimal(distributionOrder.getBrokerage()).divide(new BigDecimal(100)).setScale(2).toString());
                map.put("update_time", distributionOrder.getUpdateTime());
                Long price = xryCourseService.queryById(distributionOrder.getCourseId()).getPrice();
                map.put("price", new BigDecimal(price).divide(new BigDecimal(100)).setScale(2).toString());
                list1.add(map);
            }
        }
        return list1;
    }

    /*
     *更改用户分销订单状态为已提现
     * */
    @Override
    public void updateStatusByUid(String appUserId) {
        baseMapper.updateStatusByUid(appUserId);
    }

    @Override
    public Integer getOkNumByUidAndCid(String userId, Long courseId) {
        DistributionOrder distributionOrder = new DistributionOrder();
        distributionOrder.setUserId(userId);
        distributionOrder.setCourseId(courseId);
        return baseMapper.getOkNumByUidAndCid(distributionOrder);
    }
}
