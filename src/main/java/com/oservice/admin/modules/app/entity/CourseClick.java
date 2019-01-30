package com.oservice.admin.modules.app.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @program: oservice
 * @description: 课程分享点击记录表
 * @author: YJmiss
 * @create: 2019-01-30 10:31
 **/
@TableName("course_click")
public class CourseClick implements Serializable {
    /* CREATE TABLE `course_click` (
             `id` varchar(36) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '记录id',
             `user_id` varchar(36) COLLATE utf8_bin NOT NULL COMMENT '用户id',
             `course_id` bigint(20) unsigned NOT NULL COMMENT '课程id',
             `count` int(10) NOT NULL DEFAULT '1' COMMENT '点击数',
     PRIMARY KEY (`id`),
     KEY `user_id` (`user_id`),
     KEY `course_id` (`course_id`)
             ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 */
    private String id;
    private String userId;
    private Long courseId;
    private int countss;

    public int getCountss() {
        return countss;
    }

    public void setCountss(int countss) {
        this.countss = countss;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

}
