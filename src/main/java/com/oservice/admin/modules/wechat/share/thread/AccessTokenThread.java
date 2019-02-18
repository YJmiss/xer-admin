package com.oservice.admin.modules.wechat.share.thread;

import com.oservice.admin.modules.wechat.share.pojo.AccessToken;
import com.oservice.admin.modules.wechat.share.utils.CommonUtil;
import com.oservice.admin.modules.wechat.share.utils.ServletContextUtil;

import javax.servlet.ServletContext;

/**
 * 用appid和appsecret获取access_token，每两小时重新获取一次
 * <p>Title:AccessTokenThread</p>
 * <p>Description:</p>
 *
 * @author Shr
 * @date 2018年10月16日上午1:06:05
 */
public class AccessTokenThread implements Runnable {
    //	private static final Log log = LogFactory.getLog(AccessTokenThread.class);
    public static String appid = "";
    public static String appsecret = "";
    public static AccessToken accessToken = null;


    @Override
    public void run() {
        while (true) {
            try {
                accessToken = CommonUtil.getAccessToken(appid, appsecret);
                if (null != accessToken) {
//					log.info("获取accessToken成功");
//					log.info("accessToken初始化成功:" + accessToken.getAccessToken());
                    System.out.println("accessToken初始化成功:" + accessToken.getAccessToken());
                    // 全局缓存access_token
                    ServletContext servletContext = ServletContextUtil.getServletContext();
                    servletContext.setAttribute("access_token", accessToken.getAccessToken());

//					System.out.println("accessToken init successful :" + accessToken.getAccessToken());
                    // 有效期（秒）减去200秒，乘以1000(毫秒)——也就是在有效期的200秒前去请求新的accessToken
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
                } else {
                    // 等待一分钟，再次请求
                    System.out.println("AccessTokenThread等待一分钟，再次请求");
                    Thread.sleep(10 * 1000);
                }
            } catch (Exception e) {
                try {
                    // 等待一分钟，再次请求
                    Thread.sleep(60 * 1000);
                } catch (Exception ex) {
                    ex.printStackTrace();
//					log.error(e1);
                }
                e.printStackTrace();
//				log.error(e);
            }
        }
    }
}
