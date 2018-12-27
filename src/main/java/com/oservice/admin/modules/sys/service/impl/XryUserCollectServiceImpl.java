package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.sys.dao.XryUserCollectDao;
import com.oservice.admin.modules.sys.entity.XryUserCollectEntity;
import com.oservice.admin.modules.sys.service.XryUserCollectService;
import io.swagger.annotations.Api;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户关收藏表的接口实现类
 *
 * @author wujunquan
 * @version 1.0
 */
@Service("xryUserCollectService")
public class XryUserCollectServiceImpl extends ServiceImpl<XryUserCollectDao, XryUserCollectEntity> implements XryUserCollectService {


    @Override
    public void appUserCollectByUserId(String userId, String objId) {
        XryUserCollectEntity userCollect = new XryUserCollectEntity();
        userCollect.setObjId(objId);
        userCollect.setUserId(userId);
        userCollect.setObjType(1);
        userCollect.setObjStatus(1);
        userCollect.setCreateTime(new Date());

        baseMapper.insert(userCollect);
    }

    @Override
    public List<Map<String, Object>> appListUserCollectByUserId(String userId) {
        return baseMapper.appListUserCollectByUserId(userId);
    }

    @Override
    public Integer countCourseApplicantByCourseId(Long courseId) {
        return baseMapper.countCourseApplicantByCourseId(courseId);
    }

    @Override
    public void appDelUserCollectByCollectId(Long collectId) {
        baseMapper.deleteById(collectId);
    }
}
