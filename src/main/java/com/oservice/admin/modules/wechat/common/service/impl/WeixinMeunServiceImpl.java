package com.oservice.admin.modules.wechat.common.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oservice.admin.modules.wechat.common.dao.WeixinMeunDao;
import com.oservice.admin.modules.wechat.common.entity.WeixinMenu;
import com.oservice.admin.modules.wechat.common.service.WeixinMeunService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 微信公众号菜单服务实现
 * @author: YJmiss
 * @create: 2018-12-13 14:42
 **/
@Service
public class WeixinMeunServiceImpl extends ServiceImpl<WeixinMeunDao, WeixinMenu> implements WeixinMeunService {

    @Override
    public Boolean addMenu(WeixinMenu wxMenu) {
        return baseMapper.addMenu(wxMenu);
    }

    @Override
    public List<WeixinMenu> getParentMeunList() {
        return baseMapper.getParentMeunList();
    }

    @Override
    public List<WeixinMenu> getSonMeunList() {
        return baseMapper.getSonMeunList();
    }

    @Override
    public Boolean updateByMenuId(WeixinMenu wxMenu) {
        Boolean br = baseMapper.updateByMenuId(wxMenu);
        return br;
    }

    @Override
    public WeixinMenu getMenuById(String id) {
        return baseMapper.getMenuById(id);
    }

    @Override
    public List<WeixinMenu> getSonMenusByParentId(String parentID) {
        return baseMapper.getSonMenusByParentId(parentID);
    }

    @Override
    public Map<WeixinMenu, List<WeixinMenu>> getMenus() {
        List<WeixinMenu> okParentMeunList = baseMapper.getOkParentMeunList();
        Map<WeixinMenu, List<WeixinMenu>> map = new HashMap<>();
        for (WeixinMenu okParent : okParentMeunList) {
            List<WeixinMenu> okSonMenus = baseMapper.getSonMenusByParentId(okParent.getMenuId());
            map.put(okParent, okSonMenus);
        }
        return map;
    }

}
