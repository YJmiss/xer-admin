package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatalogEntity;
import com.oservice.admin.modules.sys.entity.XryCourseDescEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseService")
public class XryCourseServiceImpl extends ServiceImpl<XryCourseDao, XryCourseEntity> implements XryCourseService {

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
        String title = (String) params.get("title");
        // 所属类目的搜索cid
        String cid = (String) params.get("cid");
        // 根据课程id
        String courseId = (String) params.get("courseId");
        String examineStatus = (String) params.get("examineStatus");
        Page<XryCourseEntity> page = this.selectPage(new Query<XryCourseEntity>(params).getPage(), new EntityWrapper<XryCourseEntity>()
            .like(StringUtils.isNotBlank(title), "title", title)
            .like(StringUtils.isNotBlank(cid), "cid", cid)
            .like(StringUtils.isNotBlank(courseId), "id", courseId)
            .like(StringUtils.isNotBlank(examineStatus), "status", examineStatus)
        );

		return new PageUtils(page);
	}

	@Override
	public XryCourseEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryCourseEntity xryCourseEntity) {
        xryCourseEntity.setCreated(new Date());
        xryCourseEntity.setUpdated(new Date());
        baseMapper.insert(xryCourseEntity);
	}

	@Override
    public void update(XryCourseEntity xryCourseEntity) {
        xryCourseEntity.setCreated(new Date());
        xryCourseEntity.setUpdated(new Date());
        baseMapper.updateById(xryCourseEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<XryCourseEntity> treeCourse() {
        return baseMapper.treeCourse();
    }

    @Override
    public XryCourseCatalogEntity queryCourseCatalogByCourseId(Long id) {
        return baseMapper.queryCourseCatalogByCourseId(id);
    }

    @Override
    public XryCourseDescEntity queryCourseDescById(Long id) {
        return baseMapper.queryCourseDescById(id);
    }

    @Override
    public void updateCourseStatus(Map<String,Object> params) {
        baseMapper.updateCourseStatus(params);
    }

}
