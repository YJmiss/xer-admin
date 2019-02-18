package com.oservice.admin.modules.wechat.share.thread;

import com.oservice.admin.modules.wechat.share.pojo.JsApiTicket;
import com.oservice.admin.modules.wechat.share.utils.CommonUtil;
import com.oservice.admin.modules.wechat.share.utils.ServletContextUtil;

import javax.servlet.ServletContext;

/**
 * 用access_token获取jsp_ticket，每两小时重新获取一次
 * <p>Title:JsApiTicketThread</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:08:20
 */
public class JsApiTicketThread implements Runnable {

    @Override
    public void run() {
        while (true) {
            try {
                ServletContext servletContext = ServletContextUtil.getServletContext();
                String access_token = (String) servletContext.getAttribute("access_token");

                JsApiTicket jsApiTicket = null;

                if (null != access_token && !"".equals(access_token)) {
                    // 获取jsapi_ticket
                    jsApiTicket = CommonUtil.getJsApiTicket(access_token);

                    if (null != jsApiTicket) {
                        System.out.println("jsapi_ticket获取成功:" + jsApiTicket.getTicket());
                        // 全局缓存jsapi_ticket
                        servletContext.setAttribute("jsapi_ticket", jsApiTicket.getTicket());

                        Thread.sleep((jsApiTicket.getExpiresIn() - 200) * 1000);
                    }
                } else {
                    System.out.println("JsApiTicketThread等待一分钟，再次请求");
                    Thread.sleep(10 * 1000);
                }
            } catch (Exception e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                e.printStackTrace();
            }
        }
    }

}
