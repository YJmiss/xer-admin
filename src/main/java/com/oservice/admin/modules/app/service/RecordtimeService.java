package com.oservice.admin.modules.app.service;

import com.oservice.admin.modules.app.entity.XryRecordtimeEntity;

import java.util.Map;

public interface RecordtimeService {
    /**
     * 根据
     *
     * @param
     * @return
     */
    Map<String, Object> queryPlayCourseTimeByUserIdAndCourseId(long courseId, String userID);

    void addStudyTime(long courseId, long videoId, long studyTime, long sumTime, String userId);

    long queryStudyTimeByUidAndCid(XryRecordtimeEntity recordtimeEntity);
}
