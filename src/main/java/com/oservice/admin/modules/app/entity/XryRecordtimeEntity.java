package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @program: oservice
 * @description: 记录观看时间表
 * @author: YJmiss
 * @create: 2018-12-27 17:27
 **/
@TableName("xry_recordtime")
public class XryRecordtimeEntity implements Serializable {
    /* `id` varchar(50) NOT NULL COMMENT '主键',
            `video_id` bigint(20) NOT NULL COMMENT '视频ID',
            `user_id` varchar(36) NOT NULL COMMENT '用户ID',
            `course_id` bigint(20) NOT NULL COMMENT '课程ID',
            `study_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '学习时长，单位：毫秒',
            `sum_time` bigint(20) NOT NULL COMMENT '视频总时长',
            `state` int(5) NOT NULL DEFAULT '0' COMMENT '状态：0：未学完 1：已学完'
            */
    private String id;
    private long videoId;
    private String userId;
    private long courseId;
    private long studyTime;
    private long sumTime;
    private int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getVideoId() {
        return videoId;
    }

    public void setVideoId(long videoId) {
        this.videoId = videoId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getCourseId() {
        return courseId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    public long getStudyTime() {
        return studyTime;
    }

    public void setStudyTime(long studyTime) {
        this.studyTime = studyTime;
    }

    public long getSumTime() {
        return sumTime;
    }

    public void setSumTime(long sumTime) {
        this.sumTime = sumTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
