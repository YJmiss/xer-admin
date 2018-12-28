package com.oservice.admin.modules.app.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.modules.app.dao.RecordtimeDao;
import com.oservice.admin.modules.app.entity.XryRecordtimeEntity;
import com.oservice.admin.modules.app.service.RecordtimeService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description:
 * @author: YJmiss
 * @create: 2018-12-27 17:51
 **/
@Service("recordtimeService")
public class RecordtimeServiceImpl extends ServiceImpl<RecordtimeDao, XryRecordtimeEntity> implements RecordtimeService {

    @Override
    public Map<String, Object> queryPlayCourseTimeByUserIdAndCourseId(long courseId, String userID) {
        XryRecordtimeEntity recordtimeEntity = new XryRecordtimeEntity();
        Map<String, Object> map = new HashMap<>();
        recordtimeEntity.setCourseId(courseId);
        recordtimeEntity.setUserId(userID);
        List<XryRecordtimeEntity> xryRecordtimeEntities = baseMapper.queryPlayCourseTimeByUserIdAndCourseId(recordtimeEntity);
        long studyTime = 0;
        Boolean br = false;
        for (XryRecordtimeEntity xryRecordtimeEntity : xryRecordtimeEntities) {
            int state = xryRecordtimeEntity.getState();
            if (state == 1) {
                br = true;
            }
            studyTime += xryRecordtimeEntity.getStudyTime();
        }
        map.put("br", br);
        map.put("studyTime", studyTime);
        return map;
    }

    @Override
    public void addStudyTime(long courseId, long videoId, long studyTime, long sumTime, String userId) {
        XryRecordtimeEntity recordtimeEntity = new XryRecordtimeEntity();
        recordtimeEntity.setUserId(userId);
        recordtimeEntity.setCourseId(courseId);
        recordtimeEntity.setVideoId(videoId);
        recordtimeEntity.setSumTime(sumTime);
        recordtimeEntity.setStudyTime(studyTime);
        Long aLong = 0l;
        if (studyTime >= sumTime) {
            recordtimeEntity.setState(1);
        } else {
            recordtimeEntity.setState(0);
        }
        if (baseMapper.queryStudyTimeByUidAndCid(recordtimeEntity) == null) {
        } else {
            aLong = baseMapper.queryStudyTimeByUidAndCid(recordtimeEntity);
        }
        if (aLong == 0l) {
            recordtimeEntity.setId(UUIDUtils.getUUID());
            baseMapper.insertRecordtime(recordtimeEntity);
        } else {
            recordtimeEntity.setId(baseMapper.queryIdbyUidAndCid(recordtimeEntity));
            if (aLong < studyTime) {
                baseMapper.updateRecordtimeById(recordtimeEntity);
            }
        }
    }

    @Override
    public long queryStudyTimeByUidAndCid(XryRecordtimeEntity recordtimeEntity) {
        if (null == baseMapper.queryStudyTimeByUidAndCid(recordtimeEntity)) {
            return 0;
        } else {
            return baseMapper.queryStudyTimeByUidAndCid(recordtimeEntity);
        }
    }
}
