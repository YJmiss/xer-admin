package com.oservice.admin.modules.wechat.common.service;

import com.oservice.admin.modules.wechat.common.entity.WeixinMenu;

import java.util.List;
import java.util.Map;

public interface WeixinMeunService {
    /**
     * 保存菜单
     *
     * @param wxMenu 菜单
     */
    Boolean addMenu(WeixinMenu wxMenu);

    /**
     * 获取所有一级菜单
     *
     * @param
     */
    List<WeixinMenu> getParentMeunList();

    /**
     * 获取所有二级菜单
     *
     * @param
     */
    List<WeixinMenu> getSonMeunList();

    /**
     * 修改菜单状态（启用，停用，移除）
     *
     * @param
     */
    Boolean updataMenuById(WeixinMenu wxMenu);

    /**
     * 获取菜单信息
     *
     * @param id
     */
    WeixinMenu getMenuById(String id);

    /**
     * 获取二级菜单信息
     *
     * @param parentID
     */
    List<WeixinMenu> getSonMenusByParentId(String parentID);

    /**
     * 获取启动菜单MAP
     *
     * @param
     */
    Map<WeixinMenu, List<WeixinMenu>> getMenus();

}
