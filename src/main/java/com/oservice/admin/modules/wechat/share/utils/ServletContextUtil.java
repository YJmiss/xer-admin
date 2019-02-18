package com.oservice.admin.modules.wechat.share.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 全局作用域工具类，用来存放access_token和jsp_ticket
 * <p>Title:ServletContextUtil</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:08:57
 */
@Component
public final class ServletContextUtil implements ApplicationContextAware {
    // 当前Web容器
    private static WebApplicationContext applicationContext;
    // 全局作用域
    private static ServletContext serveltContext = null;

    private ServletContextUtil() {
    }

    ;

    public synchronized static ServletContext getServletContext() {
        if (null == serveltContext) {
            // 通过web容器获取全局作用域
            serveltContext = applicationContext.getServletContext();
        }
        return serveltContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ServletContextUtil.applicationContext == null) {
            ServletContextUtil.applicationContext = (WebApplicationContext) applicationContext;
        }
    }
}
