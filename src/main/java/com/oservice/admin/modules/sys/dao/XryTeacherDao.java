package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author wujunquan
 * @version 1.0
 */
@Mapper
public interface XryTeacherDao extends BaseMapper<XryTeacherEntity> {

    /**
     * 
     * @param map
     * @return
     */
    Long countTotal(@Param("params") Map<String, Object> map);

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String, Object> map);


    /**
     * 讲师认证
     * @param params
     */
    void recordExamineInfo(@Param("params") Map<String, Object> params);

    /**
     * 讲师信息查询
     *
     * @param id
     * @return
     */
    Map<String, Object> queryById(@Param("id") String id);

    /**
     * app查询讲师列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> appPageListTeacherByUserId(@Param("params") Map<String, Object> params);

    /**
     * 构造讲师树
     * @return
     */
    List<XryTeacherEntity> treeTeacher();

    /**
     * 讲师推荐、取消推荐
     * @param params
     */
    void updateTeacherRecommend(Map<String, Object> params);

    /**
     * 讲师的课程数
     * @param teacherId
     * @return
     */
    Integer countCourseByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 该讲师的学生数（所有课程学生的总数）
     *
     * @param teacherId
     * @return
     */
    Integer countStudentByTeacherId(@Param("teacherId") String teacherId);

    /**
     * app查询'明星讲师'列表:固定6个
     * @param params
     * @return
     */
    List<Map<String, Object>> appListStarTeacherByUserId(@Param("params") Map<String, Object> params);

    /**
     * app端讲师主页
     * @param teacherId
     * @return
     */
    Map<String, Object> appQueryTeacherDetailByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 她/他主讲的课程
     * @param teacherId
     * @return
     */
    List<Map<String, Object>> listTeacherCourseByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 根据课程id查询该课程的学生人数
     * @param courseId
     * @return
     */
    Integer countStudentByCourseId(@Param("courseId") Long courseId);

    /**
     * 查询用户已经关注的讲师数
     * @param userId
     * @return
     */
    Integer countUserApplicantByUserId(@Param("userId") String userId);

    /**
     * 根据讲师id查询出讲师所上的课程
     * @param teacherId
     * @return
     */
    List<Map<String, Object>> listCourseByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 把用户置为讲师后，向xry_teacher中加入该对象
     * @param xryTeacher
     */
    void insertToTeacher(XryTeacherEntity xryTeacher);
}
