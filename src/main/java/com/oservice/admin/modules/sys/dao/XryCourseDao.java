package com.oservice.admin.modules.sys.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.common.solr.SearcherItem;
import com.oservice.admin.modules.sys.entity.*;
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
public interface XryCourseDao extends BaseMapper<XryCourseEntity> {


    /**
     * 查询返回的数据总数
     * @param map
     * @return
     */
    Long countTotal(@Param("params") Map<String,Object> map);

    /**
     * 查询返回的数据总数
     * @param map
     * @return
     */
    Long examineCountTotal(@Param("params") Map<String,Object> map);

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> pageList(@Param("params") Map<String,Object> map);

    /**
     * 自定义分页查询
     * @param map
     * @return
     */
    List<Map<String, Object>> examineList(@Param("params") Map<String,Object> map);

    /**
     * 构造课程树
     * @return
     */
    List<XryCourseEntity> treeCourse();

    /**
     * 查询课程目录
     * @param id
     * @return
     */
    List<XryCourseCatalogEntity> queryCourseCatalogByCourseId(Long id);

    /**
     * 查询课程描述
     * @param id
     * @return
     */
    XryCourseDescEntity queryCourseDescById(Long id);

    /**
     * 课程上、下架
     * @param params
     */
    void updateCourseStatus(Map<String,Object> params);

    /**
     * 课程的审核
     * @param params
     */
   void recordExamineInfo(@Param("params") Map<String, Object> params);

    /**
     * 获取索引数据
     *
     * @param
     */
    List<SearcherItem> findAllItems();

    /**
     * 通过ID获取索引数据
     *
     * @param
     */
    SearcherItem findItemsById(Long id);

    /**
     * 推荐课程、取消推荐课程
     * @param params
     */
    void updateCourseRecommend(Map<String, Object> params);

    /**
     * 查询用户已经选择喜好的课程
     * @return
     */
    XryRecommendEntity listRecommendCourseCatByUserId(@Param("params") Map<String, Object> params);

    /**
     * 用户喜好添加
     * @param params
     */
    void appInsertRecommend(Map<String, Object> params);

    /**
     * 用户喜好修改
     * @param params
     */
    void appUpdateRecommend(Map<String, Object> params);

    /**
     * 好评好课查询
     */
    List<XryGoodCourseEntity> getGoodCourse();

    /**
     * 根据课程id查询学习人数
     *
     * @param courseId
     * @return
     */
    Integer countStudentByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据课程id查询评价人数
     *
     * @param courseId
     * @return
     */
    Integer countCommentByCourseId(@Param("courseId") Long courseId);

    /**
     * 根据课程id查询好评度
     *
     * @param courseId
     * @return
     */
    double countGoodPraiseByCourseId(@Param("courseId") Long courseId);

    /**
     * 查询该讲师的好评度
     *
     * @param teacherId
     * @return
     */
    double countGoodPraiseByTeacherId(@Param("teacherId") String teacherId);

    /**
     * 该讲师的课程数
     *
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
     * 该机构的课程数
     *
     * @param orgId
     * @return
     */
    Integer countCourseByOrgId(@Param("orgId") Long orgId);

    /**
     * 该机构的学生数
     *
     * @param orgId
     * @return
     */
    Integer countStudentByOrgId(@Param("orgId") Long orgId);

    /**
     * 查询课程"目录"
     * @param courseId
     * @return
     */
    List<Map<String, Object>> listCourseCatalogByCourseId(@Param("courseId") Long courseId);

    /**
     * 查询出该类目下所有课程
     * @param courseCatId
     * @return
     */
    List<Map<String, Object>> listCourseCatBySubCatId(@Param("courseCatId") Long courseCatId);

    /**
     * 查询推荐列表，不足10条的随机课程列表
     * @param map
     * @return
     */
    List<Map<String, Object>> listCourseCatBySubCatIdAndCount(@Param("params") Map<String, Object> map);

    /**
     * 查询课程章节
     * @param courseCatId
     * @return
     */
    Integer countCatalogByCourseCatId(@Param("courseCatId") Long courseCatId);

    /**
     * 查询学习人数
     * @param courseCatId
     * @return
     */
    Integer countStudentByCourseCatId(@Param("courseCatId") Long courseCatId);

    /**
     * 根据课程id查询评价信息
     * @param map
     * @return
     */
    List<Map<String, Object>> listCourseCommentByCourseId(@Param("params") Map<String, Object> map);

    /**
     * 根据目录id查询视频
     * @param catalogId
     * @return
     */
    List<Map<String, Object>> listVideoByCourseCatalogId(@Param("catalogId") Long catalogId);

    /**
     * app端课程中心接口
     * @param params
     * @return
     */
    List<Map<String, Object>> appListCourseCenter(@Param("params") Map<String, Object> params);

    /**
     * 根据课程id查询课程章节
     * @param courseId
     * @return
     */
    Integer countCatalogByCourseId(@Param("courseId") Long courseId);

    /**
     * 查询课程信息
     * @param courseId
     * @return
     */
    Map<String, Object> selectCourseAndDescById(@Param("courseId") Long courseId);

    /**
     * 根据类目id查询课程数量
     * @param catId
     * @return
     */
    Integer countCourseByCatId(@Param("catId") Long catId);

    /**
     * 重写添加课程
     *
     * @param course
     * @return
     */
    Integer insertCourse(XryCourseEntity course);

    /**
     * 获取对应课程好评率
     *
     * @param courseId
     * @return
     */
    Double getFeedback(Long courseId);

    /**
     * 查询最近浏览课程列表
     * @param params
     * @return
     */
    Map<String, Object> listCourseByUserIdAndCourseId(@Param("params") Map<String, Object> params);
}
