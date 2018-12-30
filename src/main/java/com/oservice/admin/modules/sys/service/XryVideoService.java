package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryRecordEntity;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;

import java.util.Map;

/**
 * 系统用户
 * 视频表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryVideoService extends IService<XryVideoEntity> {

    /**
     * 视频分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据视频id，查询视频
     *
     * @param id
     * @return
     */
    XryVideoEntity queryById(Long id);

    /**
     * 视频保存
     *
     * @param xryVideoEntity
     */
    void save(XryVideoEntity xryVideoEntity);

    /**
     * 视频修改
     *
     * @param xryVideoEntity
     */
    void update(XryVideoEntity xryVideoEntity);

    /**
     * 视频删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 视频的审核
     * @param record
     */
    void recordExamineInfo(XryRecordEntity record);

    /**
     * 判断与之关联的“视频”的资料是否已填
     * @param catalogId
     * @return
     */
    XryVideoEntity judeVideoIsFullByCourseId(Long catalogId);
}
