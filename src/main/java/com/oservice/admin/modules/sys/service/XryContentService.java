package com.oservice.admin.modules.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.modules.sys.entity.XryContentEntity;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 广告内容表的接口
 * @author LingDu
 * @version 1.0
 */
public interface XryContentService extends IService<XryContentEntity> {

    /**
     * 广告内容分页查询
     *
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据广告内容id，查询广告内容
     *
     * @param id
     * @return
     */
    XryContentEntity queryById(Long id);

    /**
     * 广告内容保存
     *
     * @param XryContentEntity
     */
    void save(XryContentEntity XryContentEntity);

    /**
     * 广告内容修改
     *
     * @param XryContentEntity
     */
    void update(XryContentEntity XryContentEntity);

    /**
     * 广告内容删除
     *
     * @param ids
     */
    void deleteBatch(Long[] ids);

    /**
     * 根据类目获取广告
     *
     * @param cat
     */
    List<XryContentEntity> getContentsByCat(int cat);
}
