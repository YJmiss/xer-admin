package com.oservice.admin.modules.wechat.common.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oservice.admin.modules.wechat.common.entity.WeixinMenu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WeixinMeunDao extends BaseMapper<WeixinMenu> {

    Boolean addMenu(WeixinMenu wxMenu);

    List<WeixinMenu> getParentMeunList();

    List<WeixinMenu> getSonMeunList();

    List<WeixinMenu> getSonMenusByParentId(String parentID);

    List<WeixinMenu> getOkParentMeunList();

    WeixinMenu getMenuById(String id);

    Boolean updateByMenuId(WeixinMenu wxMenu);
}
