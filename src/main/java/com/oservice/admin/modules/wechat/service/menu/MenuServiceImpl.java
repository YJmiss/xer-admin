package com.oservice.admin.modules.wechat.service.menu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oservice.admin.modules.wechat.bean.Menu;
import com.oservice.admin.modules.wechat.service.AbstractWechatService;
import com.oservice.admin.modules.wechat.service.accesstoken.AccessTokenService;
import com.oservice.admin.modules.wechat.utils.MessageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MenuServiceImpl
 * @Description: TODO
 * @Author: Administrator
 * @Date: 2018/9/20 12:46
 * @Version 1.0
 */
@Component
public class MenuServiceImpl extends AbstractWechatService implements MenuService, InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(MenuServiceImpl.class);
    private static final ObjectMapper MAPPER = new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

    @Autowired
    private AccessTokenService accessTokenService;

    @Override
    protected AccessTokenService getAccessTokenService() {
        return accessTokenService;
    }

    /*
    @Override
     public void createMenu(Menu menu) {
         Boolean isTrue;
         Preconditions.checkNotNull(menu, " parameter is not allowed to be null or empty! ");
         AccessTokenBean accessToken = checkAccessToken();
         try {
             String url = WechatConstant.CREATE_MENU_URL.replaceAll(WechatConstant.ACCESS_TOKEN, accessToken.getAccess_token());
             String paramJson = MAPPER.writeValueAsString(menu);
             String postResponse = HttpClientUtils.sendPost(url, paramJson);
             if (StringUtils.isNotEmpty(postResponse) && postResponse.indexOf("ok") > 0) {
                 isTrue = true;
             } else {
                 LOGGER.error(postResponse);
             }
         } catch (Exception e) {
             LOGGER.error(e.getMessage(), e);
         }
     }
 */
    @Override
    public String getMenu() {
        return null;
    }

    /**
     * @Description: 直接初始化创建菜单
     * @Author: xiewl
     * @param:
     * @Date: 2018/9/20 15:20
     * @Version 1.0
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        Menu menus = MessageUtils.getMenu();
        //   this.createMenu(menus);
    }
}
