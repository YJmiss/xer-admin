package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryTeacherEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 讲师表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryTeacherService extends IService<XryTeacherEntity> {

	/**
	 * 讲师分页查询
	 * @param params
	 * @return
	 */
	PageUtils queryPage(Map<String, Object> params);

	/**
	 * 根据讲师id查询讲师
	 * @param id
	 * @return
	 */
    Map<String, Object> queryById(Long id);

	/**
	 * 讲师保存
     * @param params
	 */
    void save(String[] params);

	/**
	 * 讲师删除
	 * @param ids
	 */
	void deleteBatch(Long[] ids);

	/**
	 * 讲师认证
	 * @param record
	 */
	void recordExamineInfo(XryRecordEntity record);

	/**
	 * app查询讲师列表
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> listByUserId(Map<String, Object> params);

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
	 * 计数讲师关注
	 * @param teacherId
	 * @param flag
	 */
    void updateTeacherAttention(String teacherId, Integer flag);
}
