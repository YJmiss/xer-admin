package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;

import java.util.Map;

/**
 * 系统用户
 * 记录表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryRecordService extends IService<XryRecordEntity> {

    /**
     * 记录分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据记录id，查询记录
     *
     * @param id
     * @return
     */
    XryRecordEntity queryById(Long id);

    /**
     * 记录保存
     *
     * @param XryRecordEntity
     */
    void save(XryRecordEntity XryRecordEntity);

    /**
     * 记录修改
     *
     * @param XryRecordEntity
     */
    void update(XryRecordEntity XryRecordEntity);

    /**
     * 记录删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 记录课程审核
     * @param params
     */
    void recordCourseExamine(Map<String,Object> params);
    
}
