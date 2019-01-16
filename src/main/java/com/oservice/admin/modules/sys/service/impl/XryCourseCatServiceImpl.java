package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryCourseCatDao;
import com.oservice.admin.modules.sys.entity.XryCourseCatEntity;
import com.oservice.admin.modules.sys.entity.XryCourseEntity;
import com.oservice.admin.modules.sys.service.XryCourseCatService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程类目表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryCourseCatService")
public class XryCourseCatServiceImpl extends ServiceImpl<XryCourseCatDao, XryCourseCatEntity> implements XryCourseCatService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		// 根据类目id搜索（即类目树搜索）
		String id = (String) params.get("id");
		String status = (String) params.get("status");
		Page<XryCourseCatEntity> page = this.selectPage(new Query<XryCourseCatEntity>(params).getPage(),
				new EntityWrapper<XryCourseCatEntity>()
						.like(StringUtils.isNotBlank(id), "id", id)
						.like(StringUtils.isNotBlank(status), "status", status)
			);
		return new PageUtils(page);
	}

	@Override
	public XryCourseCatEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryCourseCatEntity xryCourseCatEntity) {
        xryCourseCatEntity.setCreated(new Date());
        xryCourseCatEntity.setUpdated(new Date());
        baseMapper.insert(xryCourseCatEntity);
	}

	@Override
    public void update(XryCourseCatEntity xryCourseCatEntity) {
        xryCourseCatEntity.setCreated(new Date());
        xryCourseCatEntity.setUpdated(new Date());
        baseMapper.updateById(xryCourseCatEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

    @Override
    public List<XryCourseCatEntity> treeCourseCat(Integer flag) {
        return baseMapper.treeCourseCat(flag);
	}

	@Override
	public List<XryCourseEntity> listCourseByCourseCatalogId(Long id) {
		return  baseMapper.listCourseByCourseCatalogId(id);
	}
	
	@Override
	public List<XryCourseCatEntity> isParentCourseCatalogById(Long id) {
		return  baseMapper.isParentCourseCatalogById(id);
	}

	@Override
	public List<Map<String, Object>> listCourseCat() {
		return baseMapper.listCourseCat();
	}

	@Override
	public void updateCourseCatStatusByCatId(Map<String, Object> params) {
		baseMapper.updateCourseCatStatusByCatId(params);
	}

	@Override
	public String[] listCourseCatByParentCatId(Long courseCatId) {
		return baseMapper.listCourseCatByParentCatId(courseCatId);
	}

	@Override
	public String listRecommendCourseCatByUserId(String id) {
		return baseMapper.listRecommendCourseCatByUserId(id);
	}

	@Override
	public Map<String, Object> getCourseCatById(Long courseCatId) {
		return baseMapper.getCourseCatById(courseCatId);
	}
}
