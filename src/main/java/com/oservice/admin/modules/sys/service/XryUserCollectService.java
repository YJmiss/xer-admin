package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.modules.sys.entity.XryUserCollectEntity;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 用户收藏表接口
 *
 * @author LingDu
 * @version 1.0
 */
public interface XryUserCollectService extends IService<XryUserCollectEntity> {

    /**
     * app端用户收藏
     * @param userId
     * @param objId
     */
    void appUserCollectByUserId(String userId, String objId);

    /**
     * app端查询用户收藏列表
     * @param userId
     * @return
     */
    List<Map<String, Object>> appListUserCollectByUserId(String userId);

    /**
     * 根据课程id查询课程报名人数
     * @param courseId
     * @return
     */
    Integer countCourseApplicantByCourseId(Long courseId);

    /**
     * app端删除收藏列表的一个
     * @param collectId
     */
    void appDelUserCollectByCollectId(Long collectId);
}
