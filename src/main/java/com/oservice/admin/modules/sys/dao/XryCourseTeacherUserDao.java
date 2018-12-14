package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryCourseTeacherUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程报名和讲师关注表
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryCourseTeacherUserDao extends BaseMapper<XryCourseTeacherUserEntity> {

    /**
     * app端根据用户查询用户加入学习的课程列表
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> appPageListCourseByUserId(@Param("params") Map<String, Object> map);

    /**
     * app端根据用户查询用户关注的讲师列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> appPageListTeacherByUserId(@Param("params") Map<String, Object> params);

    /**
     * app端用户删除已经加入学习的课程
     *
     * @param params
     */
    void appDelCourseById(@Param("params") Map<String, Object> params);

    /**
     * app端用户删除已经关注的讲师
     *
     * @param params
     */
    void appDelTeacherById(@Param("params") Map<String, Object> params);

    /**
     * 根据课程id查询出报名学习该课程的所有用户
     *
     * @param courseId
     * @return
     */
    List<XryCourseTeacherUserEntity> listUserIdByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据讲师id查询关注改讲师的所有用户
     *
     * @param teacherId
     * @return
     */
    List<XryCourseTeacherUserEntity> listUserIdByTeacherId(@Param("teacherId") String teacherId);
}
