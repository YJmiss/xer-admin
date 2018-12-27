package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryUserApplicantEntity;
import com.oservice.admin.modules.sys.entity.XryUserAttentionEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户关注表接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface XryUserAttentionService extends IService<XryUserAttentionEntity> {

    /**
     * app端讲师关注
     * @param params
     * @return
     */
    Integer appSaveTeacher(Map<String, Object> params);

    /**
     * app端根据用户查询用户关注的讲师列表
     * @param params
     * @return
     */
    PageUtils appPageListTeacherByUserId(Map<String, Object> params);

    /**
     * app端用户删除已经关注的讲师
     * @param params
     */
    void appDelTeacherById(Map<String, Object> params);

    /**
     * 根据讲师id查询关注改讲师的所有用户
     *
     * @param teacherId
     * @return
     */
    List<String> listUserIdByTeacherId(String teacherId);

    /**
     * 查询讲师的关注人数列表
     * @param teacherId
     * @return
     */
    List<XryUserAttentionEntity> countAttentionByTeacherId(String teacherId);

    /**
     * 根据用户id和讲师id查询该用户是否关注了该讲师
     * @param teacherId
     * @param userId
     * @return
     */
    XryUserAttentionEntity isAttentionByUserIdAndTeacherId(String teacherId, String userId);
}
