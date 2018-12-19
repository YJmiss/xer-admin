package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.dao.XryTeacherDao;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import com.oservice.admin.modules.sys.service.XryTeacherService;
import org.apache.commons.lang.StringUtils;
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
        String pageNo = (String) params.get("page");
        String pageSize = (String) params.get("limit");
        String realName = (String) params.get("realName");
        String status = (String) params.get("status");
        map.put("pageNo",(new Integer(pageNo) - 1) * new Integer(pageSize));
        map.put("pageSize",pageSize);
        map.put("realName","%"+realName+"%");
        map.put("status",status);
        // 查询返回的数据总数page.totalCount
        Long total = baseMapper.countTotal(map);
        pageList.setTotal(total);
        // page.list 查询返回的数据list
        List<Map<String, Object>> teacherList = baseMapper.pageList(map);
        pageList.setRecords(teacherList);
		return new PageUtils(pageList);
	}

    @Override
    public Map<String, Object> queryById(Long id) {
        return baseMapper.queryById(id);
	}

	@Override
    public void save(String[] params) {
        XryTeacherEntity xryTeacherEntity = new XryTeacherEntity();
        xryTeacherEntity.setUserId(params[1]);
        xryTeacherEntity.setRealName(params[2]);
        xryTeacherEntity.setIdCard(params[3]);
        xryTeacherEntity.setIdCardFront(params[4]);
        xryTeacherEntity.setIdCardBack(params[5]);
        // type:1：个人认证  2：机构认证
        xryTeacherEntity.setType(Integer.valueOf(params[6]));
        xryTeacherEntity.setOrgId(Long.valueOf(params[7]));
        xryTeacherEntity.setStatus(Integer.valueOf(params[8]));
        xryTeacherEntity.setCreated(new Date());

        String teacherId = params[0];
        if (StringUtils.isNotBlank(teacherId)) {
            // 修改保存
            xryTeacherEntity.setId(Long.valueOf(teacherId));
            baseMapper.updateById(xryTeacherEntity);
        } else {
            // 添加保存
            baseMapper.insert(xryTeacherEntity);
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
        params.put("id",id);
        params.put("status",action);
        baseMapper.recordExamineInfo(params);
    }

    @Override
    public List<Map<String, Object>> appPageListByUserId(Map<String, Object> params) {
        return baseMapper.appPageListByUserId(params);
    }

    @Override
    public List<XryTeacherEntity> treeTeacher() {
        return baseMapper.treeTeacher();
    }

    @Override
    public void updateTeacherRecommend(Map<String, Object> params) {
            baseMapper.updateTeacherRecommend(params);
        }

    @Override
    public void updateTeacherAttention(String teacherId, Integer flag) {
        XryTeacherEntity teacher = new XryTeacherEntity();
        Integer attentionCount = teacher.getAttentionCount();
        if (1 == flag) {  // +1
            attentionCount = attentionCount + 1;
        } else {
            attentionCount = attentionCount - 1;
        }
        teacher.setAttentionCount(attentionCount);
        baseMapper.updateById(teacher);
    }


}
