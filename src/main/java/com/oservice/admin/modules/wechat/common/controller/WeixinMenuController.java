package com.oservice.admin.modules.wechat.common.controller;

import com.oservice.admin.common.utils.Result;
import com.oservice.admin.common.utils.UUIDUtils;
import com.oservice.admin.modules.wechat.common.entity.WeixinMenu;
import com.oservice.admin.modules.wechat.common.service.WeixinMeunService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: oservice
 * @description: 公众号菜单
 * @author: YJmiss
 * @create: 2018-12-13 14:40
 **/
@RestController
public class WeixinMenuController {
    @Resource
    private WeixinMeunService meunService;

    /**
     * @Description: 保存菜单信息
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @PostMapping("/sys/weChatMeun")
    public Result addMeun(@RequestBody WeixinMenu wxMenu) {
        wxMenu.setStatus(1);
        wxMenu.setMenuId(UUIDUtils.getUUID());
        Boolean br = meunService.addMenu(wxMenu);
        if (br) {
            return Result.ok();
        } else {
            return Result.error(500, "系统错误，请联系管理员！");
        }
    }

    /**
     * @Description: 获取所有一级菜单
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @GetMapping("/sys/weChatParentMeunList")
    public Result getParentMeunList() {
        Map<String, Object> map = new HashMap<>();
        List<WeixinMenu> meuns = meunService.getParentMeunList();
        if (meuns == null) {
            return Result.error(500, "系统错误，请联系管理员！");
        }
        map.put("parentMeun", meuns);
        return Result.ok(map);
    }

    /**
     * @Description: 获取所有二级菜单
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @GetMapping("/sys/weChatSonMeunList")
    public Result getSonMeunList() {
        Map<String, Object> map = new HashMap<>();
        List<WeixinMenu> meuns = meunService.getSonMeunList();
        if (meuns == null) {
            return Result.error(500, "系统错误，请联系管理员！");
        }
        map.put("sonMeun", meuns);
        return Result.ok(map);
    }

    /**
     * @Description: 更改公众号菜单状态（启动、停用、移除）
     * @Param:
     * @return:
     * @Author: YJmiss
     * @Date: 2018/12/13
     */
    @GetMapping("/sys/weChatUpdateMeun")
    public Result updataMenuById(String id, int status) {
        WeixinMenu menu = meunService.getMenuById(id);
        if (menu == null) {
            return Result.error(500, "系统错误，请联系管理员！");
        }
        if ("0".equals(menu.getParentId()) && status != 0) {
            List<WeixinMenu> sonMenus = meunService.getSonMenusByParentId(menu.getMenuId());
            if (sonMenus.size() > 0) {
                return Result.error(204, "操作失败，请先停用或移除其关联的二级菜单");
            }
            menu.setStatus(status);
        }
        menu.setStatus(status);
        Boolean br = meunService.updataMenuById(menu);
        if (br) {
            return Result.ok();
        } else {
            return Result.error(500, "系统错误，请联系管理员！");
        }
    }

}
