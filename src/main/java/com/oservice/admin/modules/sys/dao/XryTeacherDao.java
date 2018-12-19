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
    Map<String, Object> queryById(Long id);

    /**
     * app查询讲师列表
     *
     * @param params
     * @return
     */
    List<Map<String, Object>> appPageListTeacherByUserId(Map<String, Object> params);

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
}
