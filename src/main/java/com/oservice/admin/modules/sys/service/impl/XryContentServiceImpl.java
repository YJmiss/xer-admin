package com.oservice.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.common.utils.PageUtils;
import com.oservice.admin.common.utils.Query;
import com.oservice.admin.modules.sys.dao.XryContentDao;
import com.oservice.admin.modules.sys.entity.XryContentEntity;
import com.oservice.admin.modules.sys.service.XryContentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 * 课程表的接口实现类
 * @author wujunquan
 * @version 1.0
 */
@Service("xryContentService")
public class XryContentServiceImpl extends ServiceImpl<XryContentDao, XryContentEntity> implements XryContentService {

    @Override
	public PageUtils queryPage(Map<String, Object> params) {
    	String category = (String)params.get("category");
		String title = (String)params.get("title");
        Page<XryContentEntity> page = this.selectPage(new Query<XryContentEntity>(params).getPage(), new EntityWrapper<XryContentEntity>()
			.like(StringUtils.isNotBlank(category),"category",category)
			.like(StringUtils.isNotBlank(title),"title",title));
		return new PageUtils(page);
	}

	@Override
	public XryContentEntity queryById(Long id) {
		return baseMapper.selectById(id);
	}

	@Override
    public void save(XryContentEntity xryContentEntity) {
        xryContentEntity.setCreated(new Date());
        xryContentEntity.setUpdated(new Date());
        baseMapper.insert(xryContentEntity);
	}

	@Override
    public void update(XryContentEntity xryContentEntity) {
        xryContentEntity.setCreated(new Date());
        xryContentEntity.setUpdated(new Date());
        baseMapper.updateById(xryContentEntity);
	}

	@Override
	public void deleteBatch(Long[] ids) {
        this.deleteBatchIds(Arrays.asList(ids));
    }

	@Override
	public List<XryContentEntity> getContentsByCat(int cat) {
		return baseMapper.getContentsByCat(cat);
	}

}
