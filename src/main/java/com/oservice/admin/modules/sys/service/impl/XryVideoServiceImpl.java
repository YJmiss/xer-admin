package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryVideoDao;
import com.oservice.admin.modules.sys.entity.XryVideoEntity;
import com.oservice.admin.modules.sys.service.XryVideoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * 系统用户
 * 视频表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryVideoService")
public class XryVideoServiceImpl extends ServiceImpl<XryVideoDao, XryVideoEntity> implements XryVideoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        // 所属课程搜索
        String courseId = (String) params.get("courseId");
        // 所属目录搜索
        String catalogId = (String) params.get("catalogId");
        Page<XryVideoEntity> page = this.selectPage(new Query<XryVideoEntity>(params).getPage(),
                new EntityWrapper<XryVideoEntity>()
                        .like(StringUtils.isNotBlank(title), "title", title)
                        .like(StringUtils.isNotBlank(courseId),"course_id",courseId)
                        .like(StringUtils.isNotBlank(catalogId),"catalog_id",catalogId)
        );
        return new PageUtils(page);
    }

    @Override
    public XryVideoEntity queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public void save(XryVideoEntity xryVideoEntity) {
        baseMapper.insert(xryVideoEntity);
    }

    @Override
    public void update(XryVideoEntity xryVideoEntity) {
        baseMapper.updateById(xryVideoEntity);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }
}
