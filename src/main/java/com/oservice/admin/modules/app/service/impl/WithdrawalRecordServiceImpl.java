package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.modules.app.dao.WithdrawalRecordDao;
import com.oservice.admin.modules.app.entity.WithdrawalRecord;
import com.oservice.admin.modules.app.service.WithdrawalRecordService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @program: oservice
 * @description: 提现记录服务实现类
 * @author: YJmiss
 * @create: 2019-01-29 10:59
 **/
@Service("withdrawalRecordService")
public class WithdrawalRecordServiceImpl extends ServiceImpl<WithdrawalRecordDao, WithdrawalRecord> implements WithdrawalRecordService {

    @Override
    public void informationSave(WithdrawalRecord withdrawalRecord) {
        withdrawalRecord.setCreateTime(new Date());
        withdrawalRecord.setId(UUIDUtils.getUUID());
        baseMapper.insert1(withdrawalRecord);
    }

    @Override
    public void informationSave1(WithdrawalRecord withdrawalRecord) {
        withdrawalRecord.setEndTime(new Date());
        withdrawalRecord.setId(UUIDUtils.getUUID());
        baseMapper.insert1(withdrawalRecord);
    }

    @Override
    public List<Map<String, Object>> getWithdrawalRecordByUid(String appUserId) {
        List<WithdrawalRecord> list = baseMapper.getWithdrawalRecordByUid(appUserId);
        List<Map<String, Object>> list1 = new ArrayList<>();
        if (list == null || list.size() < 1) {
            return null;
        }
        for (WithdrawalRecord withdrawalRecord : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("cashWithdrawalAmount", new BigDecimal(withdrawalRecord.getCashWithdrawalAmount()).divide(new BigDecimal(100)).setScale(2).toString());
            map.put("endTime", withdrawalRecord.getEndTime());
            map.put("cardNumber", withdrawalRecord.getCardNumber());
            map.put("userName", withdrawalRecord.getUserName());
            list1.add(map);
        }
        return list1;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> param) {
        Page<Map<String, Object>> pageList = new Page<>();
        Map<String, Object> params = new HashMap<>();
        String pageNo = (String) param.get("page");
        String pageSize = (String) param.get("limit");
        String mobile = (String) param.get("mobile");
        String cTime = (String) param.get("createTime");
        String status = (String) param.get("status");
        if (null != cTime && !"".equals(cTime)) {
            String[] yearArr = cTime.split(" 年 ");
            String year = yearArr[0];
            String[] monthArr = yearArr[1].split(" 月 ");
            String month = monthArr[0];
            String day = monthArr[1].split(" 日")[0];
            cTime = year + "-" + month + "-" + day;
            params.put("createdTime", "%" + cTime + "%");
        }
        params.put("pageNo", (new Integer(pageNo) - 1) * new Integer(pageSize));
        params.put("pageSize", pageSize);
        params.put("mobile", mobile);
        params.put("status", status);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal2(params);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> courseList = baseMapper.pageList2(params);
        pageList.setRecords(courseList);
        return new PageUtils(pageList);
    }

    @Override
    public Boolean updateWithdrawal(String id) {
        Integer a = baseMapper.updateWithdrawal(id);
        if (a == null || a == 0) {
            return false;
        }
        return true;
    }

    @Override
    public WithdrawalRecord getInformationByUid(String appUserId) {
        WithdrawalRecord withdrawalRecord = baseMapper.getInformationByUid(appUserId);
        if (withdrawalRecord == null) {
            return null;
        } else {
            return withdrawalRecord;
        }
    }

    @Override
    public WithdrawalRecord selectWById(String id) {
        return baseMapper.getInformationByUid(id);
    }

    @Override
    public void informationUpdate(WithdrawalRecord withdrawalRecord) {
        baseMapper.updateById(withdrawalRecord);
    }
}
