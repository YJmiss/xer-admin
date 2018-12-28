package com.oservice.admin.modules.app.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.app.entity.XryRecordtimeEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordtimeDao extends BaseMapper<XryRecordtimeEntity> {

    List<XryRecordtimeEntity> queryPlayCourseTimeByUserIdAndCourseId(XryRecordtimeEntity recordtimeEntity);

    void insertRecordtime(XryRecordtimeEntity recordtimeEntity);

    Long queryStudyTimeByUidAndCid(XryRecordtimeEntity recordtimeEntity);

    void updateRecordtimeById(XryRecordtimeEntity recordtimeEntity);

    String queryIdbyUidAndCid(XryRecordtimeEntity recordtimeEntity);
}
